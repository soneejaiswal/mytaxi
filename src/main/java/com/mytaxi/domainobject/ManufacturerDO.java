package com.mytaxi.domainobject;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ManufacturerDO {

    @Column(name = "manufacturer")
    private String manufacturer;

    public ManufacturerDO() {
    }

    public ManufacturerDO(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
