package com.mytaxi.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.GeoCoordinate;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO {

    @JsonIgnore
    private Long id;

    @NotNull(message = "LicensePlate can not be null!")
    private String licensePlate;

    private GeoCoordinate coordinate;

    private ManufacturerDO manufacturer;

    private boolean convertible;

    private float rating;

    private short seatCount;

    private EngineType engineType;

    private Boolean selected;

    public CarDTO() {
    }

    private CarDTO(Long id, String licensePlate, GeoCoordinate coordinate, ManufacturerDO manufacturer, boolean convertible, float rating, short seatCount, EngineType engineType, boolean selected) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.coordinate = coordinate;
        this.manufacturer = manufacturer;
        this.convertible = convertible;
        this.rating = rating;
        this.seatCount = seatCount;
        this.engineType = engineType;
        this.selected = selected;
    }

    public static CarDTOBuilder newBuilder() {
        return new CarDTOBuilder();
    }

    @JsonProperty
    public Long getId()
    {
        return id;
    }

    public String getLicensePlate()
    {
        return licensePlate;
    }

    public GeoCoordinate getCoordinate()
    {
        return coordinate;
    }

    public ManufacturerDO getManufacturer() {
        return manufacturer;
    }

    public boolean isConvertible() {
        return convertible;
    }

    public float getRating() {
        return rating;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public Boolean getSelected() {
        return selected;
    }

    public static class CarDTOBuilder {
        private Long id;
        private String licensePlate;
        private GeoCoordinate coordinate;
        private ManufacturerDO manufacturer;
        private boolean convertible;
        private float rating;
        private short seatCount;
        private EngineType engineType;
        private boolean selected;

        public CarDTOBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public CarDTOBuilder setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public CarDTOBuilder setCoordinate(GeoCoordinate coordinate) {
            this.coordinate = coordinate;
            return this;
        }

        public CarDTOBuilder setManufacturer(ManufacturerDO manufacturer){
            this.manufacturer = manufacturer;
            return this;
        }

        public CarDTOBuilder setConvertible(boolean convertible) {
            this.convertible = convertible;
            return this;
        }

        public CarDTOBuilder setRating(float rating) {
            this.rating = rating;
            return this;
        }

        public CarDTOBuilder setSeatCount(short seatCount) {
            this.seatCount = seatCount;
            return this;
        }

        public CarDTOBuilder setEngineType(EngineType engineType) {
            this.engineType = engineType;
            return this;
        }

        public CarDTOBuilder setSelected(boolean selected) {
            this.selected = selected;
            return this;
        }

        public CarDTO CarDTOBuilder() {
            return new CarDTO(id, licensePlate, coordinate, manufacturer, convertible, rating, seatCount, engineType, selected);
        }
    }
}
