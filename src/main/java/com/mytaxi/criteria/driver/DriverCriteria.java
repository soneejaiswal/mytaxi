package com.mytaxi.criteria.driver;

import com.mytaxi.domainobject.DriverDO;

import java.util.List;

public interface DriverCriteria {
    List<DriverDO> meetCriteria(final List<DriverDO> drivers);
}
