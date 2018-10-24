package com.mytaxi.criteria.car;

import com.mytaxi.domainobject.CarDO;

import java.util.List;
import java.util.stream.Collectors;

public class CarRatingCarCriteria implements CarCriteria {
    private final Float rating;

    public CarRatingCarCriteria(Float rating) {
        this.rating = rating;
    }

    @Override
    public List<CarDO> meetCriteria(List<CarDO> cars) {
        return cars.stream()
                .filter(c-> c.getRating().equals(rating))
                .collect(Collectors.toList());
    }


}
