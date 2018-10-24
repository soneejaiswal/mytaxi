package com.mytaxi.domainobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.GeoCoordinate;
import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "car")
@Proxy(lazy=false)
public class CarDO {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column
    private String licensePlate;

    @Column
    private Short seatCount;

    @Column
    private Boolean convertible;

    @Column
    private Float rating;

    @Embedded
    private GeoCoordinate coordinate;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCoordinateUpdated = ZonedDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column
    private EngineType engineType;

    private ManufacturerDO manufacturer;

    @JsonIgnore
    @OneToOne(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private DriverDO driver;

    public CarDO() {
    }

//    public CarDO(String licensePlate, ManufacturerDO manufacturer ) {
//        this.licensePlate = licensePlate;
//        this.seatCount = 0;
//        this.convertible = false;
//        this.seatCount = null;
//        this.dateCoordinateUpdated = null;
//        this.engineType = EngineType.Unknown;
//        this.manufacturer = manufacturer;
//    }

    public CarDO(Long id, String licensePlate, Short seatCount, Boolean convertible, Float rating,  EngineType engineType, ManufacturerDO manufacturer) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "CarDO{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", licensePlate='" + licensePlate + '\'' +
                ", seatCount=" + seatCount +
                ", convertible=" + convertible +
                ", rating=" + rating +
                ", coordinate=" + coordinate +
                ", dateCoordinateUpdated=" + dateCoordinateUpdated +
                ", engineType=" + engineType +
                ", manufacturer='" + manufacturer + '\'' +
                ", driver=" + driver +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Short getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Short seatCount) {
        this.seatCount = seatCount;
    }

    public Boolean getConvertible() {
        return convertible;
    }

    public void setConvertible(Boolean convertible) {
        this.convertible = convertible;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public GeoCoordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(GeoCoordinate coordinate) {
        this.coordinate = coordinate;
        this.dateCoordinateUpdated = ZonedDateTime.now();
    }

    public ZonedDateTime getDateCoordinateUpdated() {
        return dateCoordinateUpdated;
    }

    public void setDateCoordinateUpdated(ZonedDateTime dateCoordinateUpdated) {
        this.dateCoordinateUpdated = dateCoordinateUpdated;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }

    public ManufacturerDO getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerDO manufacturer) {
        this.manufacturer = manufacturer;
    }

    public DriverDO getDriver() {
        return driver;
    }

    public void setDriver(DriverDO driver) {
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarDO)) return false;
        CarDO carDO = (CarDO) o;
        return Objects.equals(getId(), carDO.getId()) &&
                Objects.equals(getDateCreated(), carDO.getDateCreated()) &&
                Objects.equals(getLicensePlate(), carDO.getLicensePlate()) &&
                Objects.equals(getSeatCount(), carDO.getSeatCount()) &&
                Objects.equals(getConvertible(), carDO.getConvertible()) &&
                Objects.equals(getRating(), carDO.getRating()) &&
                Objects.equals(getCoordinate(), carDO.getCoordinate()) &&
                Objects.equals(getDateCoordinateUpdated(), carDO.getDateCoordinateUpdated()) &&
                getEngineType() == carDO.getEngineType() &&
                Objects.equals(getManufacturer(), carDO.getManufacturer()) &&
                Objects.equals(getDriver(), carDO.getDriver());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDateCreated(), getLicensePlate(), getSeatCount(), getConvertible(), getRating(), getCoordinate(), getDateCoordinateUpdated(), getEngineType(), getManufacturer(), getDriver());
    }
}
