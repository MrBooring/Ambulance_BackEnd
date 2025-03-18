package com.rapidrescue.ambulancewale.service;
import com.rapidrescue.ambulancewale.models.entity.*;
import com.rapidrescue.ambulancewale.models.enums.DriverStatus;
import com.rapidrescue.ambulancewale.models.enums.Role;
import com.rapidrescue.ambulancewale.models.request.DriverSignUpRequest;
import com.rapidrescue.ambulancewale.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AmbulanceRepository ambulanceRepository;

    @Autowired
    private AmbulanceTypeRepository ambulanceTypeRepository;

    @Autowired
    private AmbulanceFeatureRepository ambulanceFeatureRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    Logger log = Logger.getLogger(DriverService.class.getName());


    @Transactional
    public Driver signUpDriver(DriverSignUpRequest driverSignUpRequest) {
        // Step 0: Validate the Ambulance Type ID

        log.info("================adminSignUp request:=============");
        if (userRepository.existsByEmail(driverSignUpRequest.getEmail())) {
            throw new RuntimeException("User already exists with email: " + driverSignUpRequest.getEmail());
        }

        // Optionally, check by phone number if that's also unique
        if (userRepository.existsByPhone(driverSignUpRequest.getPhoneNumber())) {
            throw new RuntimeException("User already exists with phone number: " + driverSignUpRequest.getPhoneNumber());
        }

        Long ambulanceTypeId = driverSignUpRequest.getAmbulanceTypeId();
        AmbulanceType ambulanceType = ambulanceTypeRepository.findById(ambulanceTypeId)
                .orElseThrow(() -> new RuntimeException("Ambulance Type not found"));

        // Step 1: Create User
        User user = new User();
        user.setRole(Role.DRIVER);
        user.setPhone(driverSignUpRequest.getPhoneNumber());
        user.setName(driverSignUpRequest.getName());
        user.setEmail(driverSignUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(driverSignUpRequest.getPassword()));
        user.setAddress(driverSignUpRequest.getAddress());
        user.setDob(driverSignUpRequest.getDob());

        User savedUser =  userRepository.save(user);

        // Step 2: Create Ambulance
        Ambulance ambulance = new Ambulance();
        ambulance.setName(driverSignUpRequest.getAmbulanceName());
        ambulance.setDescription(driverSignUpRequest.getAmbulanceDescription());
        ambulance.setCapacity(driverSignUpRequest.getCapacity());
        ambulance.setAmbulanceType(ambulanceType);
        ambulance.setLicenceNumber(driverSignUpRequest.getLicenseNumber());
        // Save the ambulance
        Ambulance savedAmbulance = ambulanceRepository.save(ambulance);

        // Step 3: Create Driver
        Driver driver = new Driver();
        driver.setUser(savedUser);
        driver.setLat(driverSignUpRequest.getLat());
        driver.setLng(driverSignUpRequest.getLng());
        driver.setAmbulance(savedAmbulance); // Associate the ambulance with the driver
        driver.setEmail(driverSignUpRequest.getEmail());
        driver.setPhone(driverSignUpRequest.getPhoneNumber());
        driver.setDriverStatus(DriverStatus.UNAVAILABLE);
        // Save the driver
        return driverRepository.save(driver);
    }


    public Optional<Driver> getDriverByUserId(String userId) {
        return Optional.ofNullable(driverRepository.getDriverByUserUserId(Long.parseLong(userId)));
    }



}
