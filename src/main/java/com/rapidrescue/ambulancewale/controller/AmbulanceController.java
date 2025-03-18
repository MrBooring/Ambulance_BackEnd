package com.rapidrescue.ambulancewale.controller;

import com.rapidrescue.ambulancewale.models.entity.Ambulance;
import com.rapidrescue.ambulancewale.models.entity.AmbulanceType;
import com.rapidrescue.ambulancewale.models.entity.Booking;
import com.rapidrescue.ambulancewale.models.entity.Driver;
import com.rapidrescue.ambulancewale.models.request.AmbulanceBookingRequest;
import com.rapidrescue.ambulancewale.models.response.ExceptionRes;
import com.rapidrescue.ambulancewale.service.AmbulanceService;
import com.rapidrescue.ambulancewale.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
public class AmbulanceController {

    @Autowired
    AmbulanceService ambulanceService;

    @Autowired
    BookingService bookingService;
    Logger log = Logger.getLogger(AuthController.class.getName());

    @GetMapping("/public/getAmbulanceType")
    public ResponseEntity<?> getAmbulanceType() {
     try {
         log.info("============================= ambulanceTypes fetched ========================");

         List<AmbulanceType> ambulanceTypeList =  ambulanceService.getAmbulanceType();
         log.info("============================= ambulanceTypes fetched ========================");

         return ResponseEntity.status(HttpStatus.CREATED).body(ambulanceTypeList);
     } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                 .body(new ExceptionRes(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
     }
    }

    @PostMapping("/public/book")
    public ResponseEntity<?> bookAmbulance(@RequestBody AmbulanceBookingRequest request) {
        try {
            Booking booking = bookingService.createBooking(request);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ExceptionRes(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PostMapping("/public/findNearestDriver")
    public ResponseEntity<?> findNearestDriver(@RequestBody AmbulanceBookingRequest request) {
        try {


            List<Driver> booking = bookingService.findAvailableDriversWithinRange(request.getLatitude(), request.getLongitude());
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ExceptionRes(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}
