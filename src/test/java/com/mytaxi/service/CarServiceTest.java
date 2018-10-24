package com.mytaxi.service;

import com.mytaxi.criteria.car.CarCriteria;
import com.mytaxi.criteria.car.CarEngineCarCriteria;
import com.mytaxi.criteria.driver.CarRatingDriverCriteria;
import com.mytaxi.criteria.driver.DriverCriteria;
import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.BusinessException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.car.CarService;
import com.mytaxi.service.driver.DriverService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceTest {
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(CarServiceTest.class);
    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private DriverService driverService;

    @Autowired
    private DriverRepository driverRepository;

    private CarDO car1 = new CarDO();
    private CarDO car2 = new CarDO();


    @Before
    public void initiate() throws IOException {
        carRepository.deleteAll();
        driverRepository.deleteAll();
        LOG.info("Initiate -----------");
        car1.setLicensePlate("LicensePlate1");
        car1.setManufacturer(new ManufacturerDO("AUDI"));
        car1.setEngineType(EngineType.Hybrid);
        car1.setRating(Float.valueOf("4"));
        car2.setLicensePlate("LicensePlate2");
        car2.setManufacturer(new ManufacturerDO("VOLVO"));
        car2.setEngineType(EngineType.electric);
        car2.setRating(Float.valueOf("5"));
        carRepository.save(car1);
        carRepository.save(car2);

    }

    @Test
    public void testCarFetchByLicensePlate() {
        CarDO car = carRepository.findByLicensePlate("LicensePlate1");
        LOG.info("car : {}", car.toString());
        System.out.println("car.toString() = " + car.toString());
        assertNotNull(car);
        assertEquals("AUDI", car.getManufacturer().getManufacturer());
    }

    @Test
    public void testFetchAllCars() {
        List<CarDO> cars = (List<CarDO>) carRepository.findAll();
        LOG.info("Cars number : {}", cars.size());
        assertNotNull(cars);
        assertEquals(2, cars.size());
    }

    @Test
    public void testFetchUnassignedCars() {
        List<CarDO> cars = carService.findAvailableCars();
        LOG.info("Cars number : {}", cars.size());
        assertNotNull(cars);
        assertEquals(2, cars.size());
    }

    @Test
    public void testCreateCar() throws ConstraintsViolationException {
        CarDO transientCar = new CarDO();
        transientCar.setLicensePlate("testLicensePlate");
        transientCar.setEngineType(EngineType.Hybrid);
        transientCar.setManufacturer(new ManufacturerDO("HONDA"));
        transientCar.setSeatCount(Short.valueOf("4"));
        CarDO persistedCar = carService.create(transientCar);
        assertNotNull(persistedCar);
//        assertEquals(ZonedDateTime.now().getMinute(), persistedCar.getDateCreated().getMinute());
        assertNotNull(persistedCar.getId());
    }

    @Test
    public void testCarLocationUpdate() throws EntityNotFoundException {
        CarDO newCar = carService.updateLocation(car1.getId(), 10, 10);
        assertEquals(new GeoCoordinate(10, 10), newCar.getCoordinate());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetNotExistCar() throws EntityNotFoundException {
        carService.find(Long.valueOf("100"));
    }



    @Test
    public void testAssignCar() throws EntityNotFoundException, BusinessException, ConstraintsViolationException {
        DriverDO driverDO = new DriverDO("username1", "password1");
        driverDO.setOnlineStatus(OnlineStatus.ONLINE);
        DriverDO driver = driverRepository.save(driverDO);

        CarDO car = carRepository.findByLicensePlate("LicensePlate1");
        DriverDO newDriver = driverService.assignRideToDriver(car.getId(), driver.getId());
        assertNotNull(newDriver.getCar());
        assertEquals(car.getLicensePlate(), newDriver.getCar().getLicensePlate());
    }

    @Test
    public void testGetFilteredCars() {
        CarCriteria engineCriteria = new CarEngineCarCriteria(EngineType.Hybrid);
        List<CarDO> filteredCars = carService.findByCriteria(engineCriteria);
        assertEquals(car1.getId(), filteredCars.get(0).getId());
    }

    @Test
    public void testFilterDriversByCarRating() throws EntityNotFoundException, BusinessException, ConstraintsViolationException {
        DriverDO driver1 = new DriverDO("usernameX", "passwordX");
        DriverDO driver2 = new DriverDO("usernameY", "passwordY");
        driver1.setOnlineStatus(OnlineStatus.ONLINE);
        DriverDO persistedDriver1 = driverRepository.save(driver1);
        CarDO carX = new CarDO();
        carX.setLicensePlate("LicensePlateX");
        carX.setManufacturer(new ManufacturerDO("TOYOTA"));
        carX.setEngineType(EngineType.Hybrid);
        carX.setRating(Float.valueOf("4"));
        CarDO persistedCarX = carRepository.save(carX);
//        driver1.setCar(carX);
        driverService.assignRideToDriver(persistedCarX.getId(),persistedDriver1.getId());

        driver2.setOnlineStatus(OnlineStatus.ONLINE);
        DriverDO persistedDriver2 = driverRepository.save(driver2);
        CarDO carY = new CarDO();
        carY.setLicensePlate("LicensePlateY");
        carY.setManufacturer(new ManufacturerDO("VOLVO"));
        carY.setEngineType(EngineType.electric);
        carY.setRating(Float.valueOf("5"));
        CarDO persistedCarY = carRepository.save(carY);
        driverService.assignRideToDriver(persistedCarY.getId(),persistedDriver2.getId());
//        driver2.setCar(carY);


        DriverCriteria driverCriteria = new CarRatingDriverCriteria(Float.valueOf("5"));
        List<DriverDO> drivers = driverService.findByCriteria(driverCriteria);
        assertEquals("LicensePlateY", drivers.get(0).getCar().getLicensePlate());

    }

}
