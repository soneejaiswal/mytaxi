package com.mytaxi.criteria.driver;

import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainobject.ManufacturerDO;

import java.util.List;
import java.util.stream.Collectors;

public class CarManufacturerDriverCriteria implements DriverCriteria {
    private final ManufacturerDO manufacturer;

    public CarManufacturerDriverCriteria(ManufacturerDO manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public List<DriverDO> meetCriteria(List<DriverDO> drivers) {
        return drivers.stream()
                .filter(d->d.getCar().getManufacturer().equals(manufacturer))
                .collect(Collectors.toList());
    }
}
