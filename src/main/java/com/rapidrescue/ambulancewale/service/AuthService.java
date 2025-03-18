package com.rapidrescue.ambulancewale.service;

import com.rapidrescue.ambulancewale.models.entity.Admin;
import com.rapidrescue.ambulancewale.models.entity.Driver;
import com.rapidrescue.ambulancewale.models.entity.User;
import com.rapidrescue.ambulancewale.models.enums.Role;
import com.rapidrescue.ambulancewale.models.request.LoginReq;
import com.rapidrescue.ambulancewale.models.response.DriverResponse;
import com.rapidrescue.ambulancewale.repository.AdminRepository;
import com.rapidrescue.ambulancewale.repository.DriverRepository;
import com.rapidrescue.ambulancewale.repository.UserRepository;
import com.rapidrescue.ambulancewale.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    Logger log = Logger.getLogger(AuthService.class.getName());

    public Optional<?>  login(LoginReq request) {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate token using Authentication object directly
            String token = jwtUtil.generateToken(authentication);
            log.info("=============================== Authentication token ========================: \n" + token );




            // Fetch the actual user from the database
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            user.setToken(token);
            userRepository.save(user);

            if (user.getRole() == Role.DRIVER) {
                Optional<Driver> driver = driverRepository.findByPhone(user.getPhone());


                if (driver.isPresent()) {
                    log.info("=======================Driver Found ====================== \n"+driver.get().toString() );
                    DriverResponse driverLoginResponse = new DriverResponse(
                            driver.get().getDriverId(),
                            driver.get().getLat(),
                            driver.get().getLng(),
                            driver.get().getDriverStatus(),
                            driver.get().getAmbulance(),
                            user
                    );
                    log.info("=======================Driver user assigned ====================== \n"+driver.get().getUser().getName() );

                    return Optional.of(driverLoginResponse);
                }else{
                    throw new UsernameNotFoundException("Driver not found");
                }
            } else if (user.getRole() == Role.ADMIN) {

                Optional<Admin> admin = adminRepository.findByPhone(user.getPhone());


                if(admin.isPresent()) {

                    log.info("=======================Admin Found ====================== \n"+admin.get().toString() );
                    return Optional.of(admin);
                }else{
                    throw new UsernameNotFoundException("Admin not found");
                }

            }


        return Optional.empty();
    }


}
