package com.mytaxi.criteria.car;

import com.mytaxi.domainobject.CarDO;

import java.util.List;

public interface CarCriteria
{
    List<CarDO> meetCriteria(final List<CarDO> drivers);
}
