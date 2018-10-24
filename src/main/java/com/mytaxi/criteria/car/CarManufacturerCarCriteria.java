package com.mytaxi.criteria.car;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;

import java.util.List;
import java.util.stream.Collectors;

public class CarManufacturerCarCriteria implements CarCriteria {

    private final ManufacturerDO manufacturer;

    public CarManufacturerCarCriteria(ManufacturerDO manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public List<CarDO> meetCriteria(List<CarDO> cars) {
        return cars.stream()
                .filter(c-> c.getManufacturer().equals(manufacturer))
                .collect(Collectors.toList());
    }
}
