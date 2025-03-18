package com.rapidrescue.ambulancewale.repository;

import com.rapidrescue.ambulancewale.models.entity.Driver;
import com.rapidrescue.ambulancewale.models.entity.User;
import com.rapidrescue.ambulancewale.models.enums.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
    Optional<Driver> findByPhone(String phone);


    List<Driver> findByDriverStatus(DriverStatus driverStatus);

    @Query("SELECT d FROM Driver d WHERE d.driverStatus = 'AVAILABLE' AND d.driverId <> :currentDriverId")
    List<Driver> findAvailableDriversExcluding(@Param("currentDriverId")Long driverId);

    @Query("SELECT d FROM Driver d WHERE d.user.userId = :userId")
    Driver getDriverByUserUserId(@Param("userId") Long userId);

}
