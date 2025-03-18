package com.rapidrescue.ambulancewale.service;

import com.rapidrescue.ambulancewale.controller.AuthController;
import com.rapidrescue.ambulancewale.models.entity.Admin;
import com.rapidrescue.ambulancewale.models.entity.AmbulanceType;
import com.rapidrescue.ambulancewale.models.entity.User;
import com.rapidrescue.ambulancewale.models.enums.Role;
import com.rapidrescue.ambulancewale.models.request.AdminSignUpRequest;
import com.rapidrescue.ambulancewale.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

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

    Logger log = Logger.getLogger(AdminService.class.getName());


    public Admin signUpAdmin( AdminSignUpRequest request) {
  
            log.info("================adminSignUp request:=============");
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("User already exists with email: " + request.getEmail());
            }

            // Optionally, check by phone number if that's also unique
            if (userRepository.existsByPhone(request.getPhoneNumber())) {
                throw new RuntimeException("User already exists with phone number: " + request.getPhoneNumber());
            }

            // Step 1: Create User
            User user = new User();
            user.setRole(Role.ADMIN);
            user.setPhone(request.getPhoneNumber());
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setAddress(request.getAddress());
            user.setDob(request.getDob());
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());


            User savedUser =  userRepository.save(user);

            Admin admin = new Admin();
            admin.setEmail(request.getEmail());
            admin.setPhone(request.getPhoneNumber());
            admin.setUser(savedUser);
            admin.setUpdatedAt(LocalDateTime.now());
            admin.setCreatedAt(LocalDateTime.now());
             return adminRepository.save(admin);

    }
}
