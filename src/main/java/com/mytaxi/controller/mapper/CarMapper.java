package com.mytaxi.controller.mapper;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.GeoCoordinate;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class CarMapper {
    public static CarDO makeCarDO(CarDTO carDTO) {
        return new CarDO(carDTO.getId(), carDTO.getLicensePlate(), (short) carDTO.getSeatCount(), carDTO.isConvertible(), carDTO.getRating(), carDTO.getEngineType(), carDTO.getManufacturer());

    }

    public static CarDTO makeCarDTO(CarDO carDo) {
        CarDTO.CarDTOBuilder carDTOBuilder = CarDTO.newBuilder()
                .setId(carDo.getId())
                .setLicensePlate(carDo.getLicensePlate())
                .setManufacturer(carDo.getManufacturer())
                .setEngineType(carDo.getEngineType())
                .setRating(carDo.getRating())
                .setSeatCount(carDo.getSeatCount())
                .setCoordinate(carDo.getCoordinate())
                .setSelected(carDo.getDriver() != null ? true : false)
                .setConvertible(carDo.getConvertible());
        GeoCoordinate coordinate = carDo.getCoordinate();
        if (coordinate != null) {
            carDTOBuilder.setCoordinate(coordinate);
        }

        return carDTOBuilder.CarDTOBuilder();
    }

    public static List<CarDTO> makeCarDTOList(Collection<CarDO> cars) {
        return cars.stream()
                .map(CarMapper::makeCarDTO)
                .collect(Collectors.toList());
    }
}