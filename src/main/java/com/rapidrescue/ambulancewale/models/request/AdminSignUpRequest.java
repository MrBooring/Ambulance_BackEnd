package com.rapidrescue.ambulancewale.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;

@Data
public class AdminSignUpRequest {

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
}
