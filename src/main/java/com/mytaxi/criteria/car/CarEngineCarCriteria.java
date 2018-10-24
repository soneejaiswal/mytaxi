package com.mytaxi.criteria.car;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.EngineType;

import java.util.List;
import java.util.stream.Collectors;

public class CarEngineCarCriteria implements CarCriteria {
    private final EngineType engineType;

    public CarEngineCarCriteria(EngineType engineType) {
        this.engineType = engineType;
    }

    @Override
    public List<CarDO> meetCriteria(List<CarDO> cars) {
        return cars.stream()
                .filter(c -> engineType == c.getEngineType())
                .collect(Collectors.toList());

    }
}
