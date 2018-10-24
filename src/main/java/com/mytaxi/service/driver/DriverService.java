package com.mytaxi.service.driver;

import com.mytaxi.criteria.driver.DriverCriteria;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.BusinessException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

import java.util.List;

public interface DriverService {

    DriverDO find(Long driverId) throws EntityNotFoundException;

    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

    void delete(Long driverId) throws EntityNotFoundException;

    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

    List<DriverDO> find(OnlineStatus onlineStatus);

    DriverDO assignRideToDriver(Long carId, Long driverId) throws EntityNotFoundException, ConstraintsViolationException, BusinessException;

    DriverDO unassignRideFromDriver(Long driverId) throws EntityNotFoundException;

    List<DriverDO> findByCriteria(DriverCriteria criteria);

}
