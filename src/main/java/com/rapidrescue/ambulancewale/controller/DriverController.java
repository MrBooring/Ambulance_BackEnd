package com.rapidrescue.ambulancewale.controller;

import com.rapidrescue.ambulancewale.models.response.ExceptionRes;
 import com.rapidrescue.ambulancewale.security.JwtUtil;
import com.rapidrescue.ambulancewale.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.logging.Logger;

@RestController
public class DriverController {
    @Autowired
    DriverService driverService;


    @Autowired
    JwtUtil jwtUtil;

    Logger log = Logger.getLogger(DriverController.class.getName());

    @GetMapping("/driver/getDriverById")
    public ResponseEntity<?> getDriverById(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            log.info("============================= getDriverById start ========================");
                log.info("Auth header ==== "+authorizationHeader);
            var id = jwtUtil.extractUserId(authorizationHeader);
            log.info("======================= "+id+"==========================");

            Optional<?> driverResponse  = driverService.getDriverByUserId(id);

            log.info("============================= getDriverById End ========================");
            return ResponseEntity.status(
                    HttpStatus.OK).body(driverResponse);



        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ExceptionRes(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}
