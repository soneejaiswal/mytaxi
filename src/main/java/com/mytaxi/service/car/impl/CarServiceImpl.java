package com.mytaxi.service.car.impl;

import com.mytaxi.criteria.car.CarCriteria;
import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.car.CarService;

import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(CarServiceImpl.class);

    private final CarRepository carRepository;

    public CarServiceImpl(final CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * Finds car by id
     * @exception  EntityNotFoundException
     * @param carId
     * @return carDO
     */
    @Override
    public CarDO find(Long carId) throws EntityNotFoundException {
        CarDO carDO =  carRepository.findCarById(carId);
        if(carDO==null){
            throw new EntityNotFoundException("Could not find entity with id: " + carId);
        }
        LOG.info("car : {}", carDO.toString());
        return carDO;
    }

    /**
     * Finds all cars which are not assigned to any driver
     * @return DriverDO
     */
    @Override
    public List<CarDO> findAvailableCars() {
        return carRepository.findByDriverIsNull();
    }

    /**
     * Create a new car
     * @exception  ConstraintsViolationException
     * @return CarDO
     */
    @Override
    public CarDO create(CarDO carDO) throws ConstraintsViolationException {
        CarDO car;
        try {
            car = carRepository.save(carDO);
        }
        catch (DataIntegrityViolationException e){
            LOG.warn("Some constraints are thrown due to driver creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        LOG.info("car : {}", car.toString());
        return car;
    }
    /**
     * delete a car by id.
     *
     * @param carId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public void delete(Long carId) throws EntityNotFoundException {
        CarDO car = carRepository.findCarById(carId);
        if(car!=null){
            carRepository.deleteById(carId);
        }
    }
    /**
     * Update location of car
     * @exception  EntityNotFoundException
     */
    @Override
    public CarDO updateLocation(long carId, double longitude, double latitude) throws EntityNotFoundException {
        CarDO carDO = carRepository.findCarById(carId);
        if (carDO == null)
            throw new EntityNotFoundException("car not found"+carId);
        carDO.setCoordinate(new GeoCoordinate(latitude, longitude));
        LOG.info("car : {}", carDO.toString());
        return carDO;
    }

    /**
     * Finds all cars
     */
    @Override
    public List<CarDO> getCars() {
        return (List<CarDO>) carRepository.findAll();
    }

    /**
     * Finds all cars
     * @param criteria
     */
    @Override
    public List<CarDO> findByCriteria(CarCriteria criteria) {
        List<CarDO> cars;
        cars = (List<CarDO>) carRepository.findAll();
        if(criteria==null){
            return cars;
        }
        return criteria.meetCriteria(cars);
    }
}
