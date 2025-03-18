package com.rapidrescue.ambulancewale.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@Entity
public class Ambulance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ambulance_id;

    @NotBlank( message = "Name Cannot Be Blank")
    @Length(min = 4, max = 50, message = "Name can be min 4 or max 50 character long" )
    private String name;

    @NotBlank( message = "description Cannot Be Blank")
    @Length(min = 4, max = 100, message = "description can be min 4 or max 100 character long" )
    private String description;

    @NotBlank( message = "Licence Number Cannot Be Blank")
    @Length(min = 4, max = 12, message = "Licence Number can be min 4 or max 12 character long" )
    private String licenceNumber;


    @NotNull(message = "Capacity Cannot Be Null")
    @Min(value = 1, message = "Capacity must be greater than or equal to 1")
    public int capacity;


    // Many ambulances can belong to one ambulance type
    @ManyToOne
    @JoinColumn(name = "ambulance_type_id") // Foreign key column for Ambulance_Type
    private AmbulanceType ambulanceType;


    public Long getAmbulance_id() {
        return ambulance_id;
    }

    public void setAmbulance_id(Long ambulance_id) {
        this.ambulance_id = ambulance_id;
    }

    public @NotBlank(message = "Name Cannot Be Blank") @Length(min = 4, max = 30, message = "Name can be min 4 or max 30 character long") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name Cannot Be Blank") @Length(min = 4, max = 30, message = "Name can be min 4 or max 30 character long") String name) {
        this.name = name;
    }

    public @NotBlank(message = "description Cannot Be Blank") @Length(min = 4, max = 100, message = "description can be min 4 or max 100 character long") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "description Cannot Be Blank") @Length(min = 4, max = 100, message = "description can be min 4 or max 100 character long") String description) {
        this.description = description;
    }

    public @NotBlank(message = "Licence Number Cannot Be Blank") @Length(min = 4, max = 12, message = "Licence Number can be min 4 or max 12 character long") String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(@NotBlank(message = "Licence Number Cannot Be Blank") @Length(min = 4, max = 12, message = "Licence Number can be min 4 or max 12 character long") String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    @NotNull(message = "Capacity Cannot Be Null")
    @Min(value = 1, message = "Capacity must be greater than or equal to 1")
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(@NotNull(message = "Capacity Cannot Be Null") @Min(value = 1, message = "Capacity must be greater than or equal to 1") int capacity) {
        this.capacity = capacity;
    }

    public AmbulanceType getAmbulanceType() {
        return ambulanceType;
    }

    public void setAmbulanceType(AmbulanceType ambulanceType) {
        this.ambulanceType = ambulanceType;
    }


}
