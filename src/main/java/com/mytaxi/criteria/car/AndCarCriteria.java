package com.mytaxi.criteria.car;

import com.mytaxi.domainobject.CarDO;

import java.util.List;

public class AndCarCriteria implements CarCriteria
{

    private CarCriteria criteria;
    private CarCriteria otherCriteria;


    public AndCarCriteria(CarCriteria criteria, CarCriteria otherCriteria)
    {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }


    @Override
    public List<CarDO> meetCriteria(List<CarDO> cars)
    {
        List<CarDO> firstCriteriaItems = criteria.meetCriteria(cars);
        return otherCriteria.meetCriteria(firstCriteriaItems);

    }

}
