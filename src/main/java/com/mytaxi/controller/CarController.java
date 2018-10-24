package com.mytaxi.controller;

import com.mytaxi.controller.mapper.CarMapper;
import com.mytaxi.criteria.car.CarCriteria;
import com.mytaxi.criteria.car.CriteriaBuilder;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.datatransferobject.FilterDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.exception.InvalidCriteriaException;
import com.mytaxi.service.car.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
@RestController
@RequestMapping("v1/cars")
public class CarController {
    private final CarService carService;

    public CarController(final CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/{carId}")
    public CarDTO getCar(@Valid @PathVariable long carId) throws EntityNotFoundException {
        return CarMapper.makeCarDTO(carService.find(carId));
    }

    @GetMapping("/allCars")
    public List<CarDTO> findAllCars()throws EntityNotFoundException{
        return CarMapper.makeCarDTOList((Collection<CarDO>) carService.getCars());
    }

    @GetMapping
    public List<CarDTO> findAvailableCars() throws ConstraintsViolationException, EntityNotFoundException {
        return CarMapper.makeCarDTOList(carService.findAvailableCars());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@Valid @RequestBody CarDTO carDTO) throws ConstraintsViolationException {
        CarDO carDO = CarMapper.makeCarDO(carDTO);
        return CarMapper.makeCarDTO(carService.create(carDO));
    }

    @DeleteMapping("/{carId}")
    public void deleteCar(@Valid @RequestParam long carId) throws EntityNotFoundException {
        carService.delete(carId);
    }

    @PutMapping("/{carId}")
    public CarDTO updateLocation(
            @Valid @RequestParam long carId, @RequestParam double longitude, @RequestParam double latitude)
            throws EntityNotFoundException {
        return CarMapper.makeCarDTO(carService.updateLocation(carId, longitude, latitude));
    }

    @PostMapping("/filter")
    public List<CarDTO> filterCars(@Valid @RequestBody FilterDTO filterCriteria) throws InvalidCriteriaException {
        CarCriteria criteria = CriteriaBuilder.build(filterCriteria);
        return CarMapper.makeCarDTOList(carService.findByCriteria(criteria));
    }

}
