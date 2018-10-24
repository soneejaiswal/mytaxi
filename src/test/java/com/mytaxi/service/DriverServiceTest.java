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
import com.mytaxi.domainvalue.OnlineStatus;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DriverServiceTest {
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DriverServiceTest.class);
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
    private DriverDO driver1 = new DriverDO("usernameX", "passwordX");
    private DriverDO driver2 = new DriverDO("usernameY", "passwordY");

    @Before
    public void initiate() throws IOException {
        carRepository.deleteAll();
        driverRepository.deleteAll();
        LOG.info("Initiate -----------");
        car1.setLicensePlate("LicensePlateX");
        car1.setManufacturer(new ManufacturerDO("AUDI"));
        car1.setEngineType(EngineType.Hybrid);
        car1.setRating(Float.valueOf("4"));
        car2.setLicensePlate("LicensePlateY");
        car2.setManufacturer(new ManufacturerDO("VOLVO"));
        car2.setEngineType(EngineType.electric);
        car2.setRating(Float.valueOf("5"));
        carRepository.save(car1);
        carRepository.save(car2);

        driver1.setOnlineStatus(OnlineStatus.ONLINE);
        driver1.setCar(car1);
        driverRepository.save(driver1);

        driver2.setOnlineStatus(OnlineStatus.ONLINE);
        driver2.setCar(car2);
        driverRepository.save(driver2);
    }



    @Test
    public void testFilterDriversByCarRating(){
        DriverCriteria driverCriteria = new CarRatingDriverCriteria(Float.valueOf("5"));
        List<DriverDO> drivers =  driverService.findByCriteria(driverCriteria);
        assertEquals("LicensePlateY", drivers.get(0).getCar().getLicensePlate());
    }

    @Test
    public void testGetFilteredCars() {
        CarCriteria engineCriteria = new CarEngineCarCriteria(EngineType.Hybrid);
        List<CarDO> filteredCars = carService.findByCriteria(engineCriteria);
        assertEquals(car1.getId(), filteredCars.get(0).getId());
    }
}
