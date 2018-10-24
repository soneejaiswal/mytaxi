package com.mytaxi.criteria.driver;

import com.mytaxi.domainobject.DriverDO;

import java.util.List;
import java.util.stream.Collectors;

public class CarRatingDriverCriteria implements DriverCriteria {
    private final Float rating;

    public CarRatingDriverCriteria(Float rating) {
        this.rating = rating;
    }

    @Override
    public List<DriverDO> meetCriteria(List<DriverDO> drivers) {
        return drivers.stream()
                .filter(d->d.getCar().getRating().equals(rating))
                .collect(Collectors.toList());
    }
}
