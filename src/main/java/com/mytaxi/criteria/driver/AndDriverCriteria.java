package com.mytaxi.criteria.driver;

import com.mytaxi.domainobject.DriverDO;

import java.util.List;


public class AndDriverCriteria implements DriverCriteria {
    private DriverCriteria criteria;
    private DriverCriteria otherCriteria;

    public AndDriverCriteria(DriverCriteria criteria, DriverCriteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public List<DriverDO> meetCriteria(List<DriverDO> drivers) {
        List<DriverDO> firstCriteriaItems = criteria.meetCriteria(drivers);
        return otherCriteria.meetCriteria(firstCriteriaItems);
    }
}
