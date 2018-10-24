package com.mytaxi.service.driver;

import com.mytaxi.criteria.driver.DriverCriteria;
import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.BusinessException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final DriverRepository driverRepository;

    private final CarRepository carRepository;

    public DefaultDriverService(final DriverRepository driverRepository, final CarRepository carRepository) {
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
    }

    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException {
        DriverDO driver;
        try {
            driver = driverRepository.save(driverDO);
        } catch (DataIntegrityViolationException e) {
            LOG.warn("Some constraints are thrown due to driver creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus) {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }

    /**
     * Finds a driver by id.
     *
     * @param driverId
     * @return driverDO
     */
    private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException {
        DriverDO driverDO = driverRepository.findByDriverId(driverId);
        if (driverDO == null) {
            throw new EntityNotFoundException("Could not find entity with id: " + driverId);
        }
        return driverDO;
    }

    /**
     * Assigns a ride to a driver
     * if referred car not found throws Exception
     * if car is assigned to another driver throws exception
     * if driver has another car first its current car gets unassigned
     * @exception  EntityNotFoundException
     * @exception  ConstraintsViolationException
     * @exception BusinessException
     * @param carId
     * @param driverId
     * @return DriverDO
     */
    @Override
    @Transactional
    public DriverDO assignRideToDriver(Long carId, Long driverId) throws EntityNotFoundException, ConstraintsViolationException, BusinessException {
        CarDO car = carRepository.findCarById(carId);
        if (car == null) {
            throw new EntityNotFoundException("car not found or already assigned " + carId);
        }
        DriverDO driver = driverRepository.findByDriverId(driverId);
        if(car.getDriver()!=null && car.getDriver().getId()!=driverId){
            throw new BusinessException("car is already assigned to another driver"+carId);
        }
        if(driver.getCar()!= null){
            //if there is a car already assigned to driver, first need to be un assigned
            unassignCar(driver.getCar().getId());
        }
        driver.setCar(car);
        driverRepository.save(driver);
        LOG.info("car : {}", car.toString());
        LOG.info("driver assigned car : {}", driver.getCar());
        return driver;

    }

    /**
     * Unassigns a ride from a driver
     * @exception  EntityNotFoundException
     * @param driverId
     * @return DriverDO
     */
    @Override
    public DriverDO unassignRideFromDriver(Long driverId) throws EntityNotFoundException {
        LOG.info("car is assigning to driver");
        DriverDO driver = driverRepository.findByDriverId(driverId);
        if(driver.getCar()!=null) {
            Long carId = driver.getCar().getId();
            unassignCar(carId);
        }
        driver.setCar(null);
        driverRepository.save(driver);
        LOG.info("driver unassigned car");
        return driver;
    }


    private void unassignCar(Long carId) throws EntityNotFoundException{
        LOG.info("car is getting unassigned from diver");
        CarDO car = carRepository.findCarById(carId);
        car.setDriver(null);
        carRepository.save(car);
        LOG.info("car unassigned car : {}", car.toString());
    }

    @Override
    public List<DriverDO> findByCriteria(DriverCriteria criteria) {
        List<DriverDO> drivers = (List<DriverDO>) driverRepository.findAll();
        if(criteria==null){
            return drivers;
        }
        return criteria.meetCriteria(drivers);
    }
}
