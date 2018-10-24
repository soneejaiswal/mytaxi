package com.mytaxi.criteria.driver;

import com.mytaxi.domainobject.DriverDO;

import java.util.List;

public class OrDriverCriteria implements DriverCriteria {
    private DriverCriteria criteria;
    private DriverCriteria otherCriteria;

    public OrDriverCriteria(DriverCriteria criteria, DriverCriteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public List<DriverDO> meetCriteria(List<DriverDO> drivers) {
        List<DriverDO> firstCriteriaItems = criteria.meetCriteria(drivers);
        List<DriverDO> otherCrietriaItems = criteria.meetCriteria(drivers);
        for(DriverDO driver:otherCrietriaItems){
            if(!firstCriteriaItems.contains(driver)){
                firstCriteriaItems.add(driver);
            }
        }
        return firstCriteriaItems;
    }
}
