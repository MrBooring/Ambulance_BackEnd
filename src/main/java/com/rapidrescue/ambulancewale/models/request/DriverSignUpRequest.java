package com.rapidrescue.ambulancewale.models.request;

import com.rapidrescue.ambulancewale.models.entity.*;
import com.rapidrescue.ambulancewale.models.enums.DriverStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;
import java.util.List;

public class DriverSignUpRequest {


     private Timestamp dob;


    @NotBlank(message = "Driver name cannot be blank")
    private String name;

    @Email
    private String email;

    private String password;

    @Length(min = 4, max = 50, message = "description can be min 4 or max 50 character long" )
    private String address;

    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;


    @NotNull
    private double lat;

    @NotNull
    private double lng;


    //======ambulance details

    @NotBlank(message = "License number cannot be blank")
    private String licenseNumber;

    @NotBlank(message = "Ambulance name cannot be blank")
    private String ambulanceName;

    @NotBlank(message = "Ambulance description cannot be blank")
    private String ambulanceDescription;

    @NotNull(message = "Capacity cannot be null")
    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;

    @NotNull(message = "Ambulance type ID cannot be null")
    private Long ambulanceTypeId;

    public Timestamp getDob() {
        return dob;
    }

    public void setDob(Timestamp dob) {
        this.dob = dob;
    }

    public @NotBlank(message = "Driver name cannot be blank") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Driver name cannot be blank") String name) {
        this.name = name;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public @Length(min = 4, max = 50, message = "description can be min 4 or max 50 character long") String getAddress() {
        return address;
    }

    public void setAddress(@Length(min = 4, max = 50, message = "description can be min 4 or max 50 character long") String address) {
        this.address = address;
    }

    public @NotBlank(message = "Phone number cannot be blank") String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotBlank(message = "Phone number cannot be blank") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NotNull
    public double getLat() {
        return lat;
    }

    public void setLat(@NotNull double lat) {
        this.lat = lat;
    }

    @NotNull
    public double getLng() {
        return lng;
    }

    public void setLng(@NotNull double lng) {
        this.lng = lng;
    }

    public @NotBlank(message = "License number cannot be blank") String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(@NotBlank(message = "License number cannot be blank") String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public @NotBlank(message = "Ambulance name cannot be blank") String getAmbulanceName() {
        return ambulanceName;
    }

    public void setAmbulanceName(@NotBlank(message = "Ambulance name cannot be blank") String ambulanceName) {
        this.ambulanceName = ambulanceName;
    }

    public @NotBlank(message = "Ambulance description cannot be blank") String getAmbulanceDescription() {
        return ambulanceDescription;
    }

    public void setAmbulanceDescription(@NotBlank(message = "Ambulance description cannot be blank") String ambulanceDescription) {
        this.ambulanceDescription = ambulanceDescription;
    }

    @NotNull(message = "Capacity cannot be null")
    @Min(value = 1, message = "Capacity must be at least 1")
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(@NotNull(message = "Capacity cannot be null") @Min(value = 1, message = "Capacity must be at least 1") int capacity) {
        this.capacity = capacity;
    }

    public @NotNull(message = "Ambulance type ID cannot be null") Long getAmbulanceTypeId() {
        return ambulanceTypeId;
    }

    public void setAmbulanceTypeId(@NotNull(message = "Ambulance type ID cannot be null") Long ambulanceTypeId) {
        this.ambulanceTypeId = ambulanceTypeId;
    }
}
