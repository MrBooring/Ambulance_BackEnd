package com.rapidrescue.ambulancewale.controller;

import com.rapidrescue.ambulancewale.models.entity.Admin;
import com.rapidrescue.ambulancewale.models.entity.Driver;
import com.rapidrescue.ambulancewale.models.request.AdminSignUpRequest;
import com.rapidrescue.ambulancewale.models.request.DriverSignUpRequest;
import com.rapidrescue.ambulancewale.models.request.LoginReq;
import com.rapidrescue.ambulancewale.models.response.DriverResponse;
import com.rapidrescue.ambulancewale.models.response.ExceptionRes;
import com.rapidrescue.ambulancewale.service.AdminService;
import com.rapidrescue.ambulancewale.service.AuthService;
import com.rapidrescue.ambulancewale.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private DriverService driverService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthService authService;

    Logger log = Logger.getLogger(AuthController.class.getName());

    @PostMapping("/driverLogin")
    public ResponseEntity<?> driverLogin(@RequestBody LoginReq request) {
        try {
            log.info("================Login request:=============");
            Optional<?> driverLoginResponse = authService.login(request);
            log.info("================Got Driver :============="+driverLoginResponse.toString()  );
            return ResponseEntity.status(
                    HttpStatus.OK).body(driverLoginResponse);


        }
        catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ExceptionRes("Invalid username or password", HttpStatus.UNAUTHORIZED.value()));
        }catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ExceptionRes("Invalid username or password", HttpStatus.NOT_FOUND.value()));
        }
        catch (Exception e){
            return ResponseEntity.status(e.hashCode())
                    .body(
                    new ExceptionRes(e.getMessage(), e.hashCode())
            );
        }
    }

    @PostMapping("/AdminLogin")
    public ResponseEntity<?> adminLogin(@RequestBody LoginReq request) {
        try {
            log.info("================Login request:=============");
            Optional<?> adminLoginRes = authService.login(request);
            log.info("================Got admin :============="+adminLoginRes.toString()  );
            return ResponseEntity.status(
                    HttpStatus.OK).body(adminLoginRes);


        }
        catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ExceptionRes("Invalid username or password", HttpStatus.UNAUTHORIZED.value()));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ExceptionRes("Invalid username or password", HttpStatus.NOT_FOUND.value()));
        } catch (Exception e) {
            // Using INTERNAL_SERVER_ERROR for any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ExceptionRes(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }


    @PostMapping("/adminSignUp")
    public ResponseEntity<?> adminSignUp(@Valid @RequestBody AdminSignUpRequest request){
        try {
            log.info("================adminSignUp request:=============");
            Admin createdAdmin = adminService.signUpAdmin(request);
            log.info("================admin created request:=============");
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAdmin);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ExceptionRes("Invalid username or password", HttpStatus.UNAUTHORIZED.value()));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ExceptionRes("Invalid username or password", HttpStatus.NOT_FOUND.value()));
        } catch (Exception e) {
            // Using INTERNAL_SERVER_ERROR for any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ExceptionRes(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PostMapping("/driverSignUp")
    public ResponseEntity<?> driverSignUp(@Valid @RequestBody DriverSignUpRequest request) {
        try {
            Driver createdDriver = driverService.signUpDriver(request);

            return new ResponseEntity<>(createdDriver, HttpStatus.CREATED);

        }  catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ExceptionRes("Invalid username or password", HttpStatus.UNAUTHORIZED.value()));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ExceptionRes("Invalid username or password", HttpStatus.NOT_FOUND.value()));
        } catch (Exception e) {

            // Using INTERNAL_SERVER_ERROR for any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ExceptionRes(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
