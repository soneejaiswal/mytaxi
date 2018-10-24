package com.mytaxi.criteria.car;

import com.mytaxi.domainobject.CarDO;

import java.util.List;

public class OrCarCriteria implements CarCriteria {
    private CarCriteria criteria;
    private CarCriteria otherCrietria;

    public OrCarCriteria(CarCriteria criteria, CarCriteria otherCrietria) {
        this.criteria = criteria;
        this.otherCrietria = otherCrietria;
    }

    @Override
    public List<CarDO> meetCriteria(List<CarDO> cars) {
        List<CarDO> firstCriteriaItems = criteria.meetCriteria(cars);
        List<CarDO> otherCrietriaItems = otherCrietria.meetCriteria(cars);

        for (CarDO car : otherCrietriaItems) {
            if (!firstCriteriaItems.contains(car)) {
                firstCriteriaItems.add(car);
            }
        }
        return firstCriteriaItems;
    }
}
