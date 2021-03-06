package com.mytaxi.controller;

import com.mytaxi.controller.mapper.DriverMapper;
import com.mytaxi.criteria.driver.DriverCriteria;
import com.mytaxi.criteria.driver.DriverCriteriaBuilder;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.datatransferobject.FilterDTO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.BusinessException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.exception.InvalidCriteriaException;
import com.mytaxi.service.driver.DriverService;

import java.util.List;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * All operations with a driver will be routed by this controller.
 * Assigning and unassigning a car to driver will be routed here
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController {

    private final DriverService driverService;


    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/{driverId}")
    public DriverDTO getDriver(@Valid @PathVariable long driverId) throws EntityNotFoundException {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }


    @DeleteMapping("/{driverId}")
    public void deleteDriver(@Valid @PathVariable long driverId) throws EntityNotFoundException {
        driverService.delete(driverId);
    }


    @PutMapping("/{driverId}")
    public void updateLocation(
            @Valid @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
            throws ConstraintsViolationException, EntityNotFoundException {
        driverService.updateLocation(driverId, longitude, latitude);
    }


    @GetMapping
    public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus)
            throws ConstraintsViolationException, EntityNotFoundException {
        return DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
    }

    @PutMapping
    public DriverDO assignCarToDriver(@RequestParam Long carId, @RequestParam Long driverId) throws EntityNotFoundException, ConstraintsViolationException, BusinessException {
        return driverService.assignRideToDriver(carId, driverId);
    }

    @PutMapping("/unassignDriver{/driverId}")
    public DriverDO unassignCarFromDriver(@RequestParam Long driverId) throws EntityNotFoundException {
        return driverService.unassignRideFromDriver(driverId);
    }

    @PostMapping("/filter")
    public List<DriverDTO> filterDrivers(@Valid @RequestBody FilterDTO filterCriteria) throws EntityNotFoundException, InvalidCriteriaException {
        DriverCriteria driverCriteria = DriverCriteriaBuilder.build(filterCriteria);
        return DriverMapper.makeDriverDTOList(driverService.findByCriteria(driverCriteria));
    }


}
