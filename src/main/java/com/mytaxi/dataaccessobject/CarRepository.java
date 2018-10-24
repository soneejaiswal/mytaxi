package com.mytaxi.dataaccessobject;

import com.mytaxi.domainobject.CarDO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends CrudRepository<CarDO, Long>
{

    @Query(value = "SELECT * from Car car WHERE car.id=:id", nativeQuery = true)
    CarDO findCarById(@Param("id") Long id);


    CarDO findByLicensePlate(String licensePlate);


    List<CarDO> findByDriverIsNull();

    //    CarDO findOne(Long carId);
}
