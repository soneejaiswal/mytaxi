package com.mytaxi.service.car;

import com.mytaxi.criteria.car.CarCriteria;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

import java.util.List;

public interface CarService {
    CarDO find(Long carId) throws EntityNotFoundException;

    CarDO create(CarDO carDO) throws ConstraintsViolationException;

    void delete(Long carId) throws EntityNotFoundException;

    CarDO updateLocation(long carId, double longitude, double latitude) throws EntityNotFoundException;

    Iterable<CarDO> getCars();

    List<CarDO> findAvailableCars();

    List<CarDO> findByCriteria(CarCriteria criteria);
}
