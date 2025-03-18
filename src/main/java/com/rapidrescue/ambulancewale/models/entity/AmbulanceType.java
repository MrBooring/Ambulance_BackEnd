package com.rapidrescue.ambulancewale.models.entity;

import com.rapidrescue.ambulancewale.repository.AmbulanceFeatureRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data

public class AmbulanceType extends BaseEntity {

    public AmbulanceType(String name, String description, int capacity) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
        // Fetch the AmbulanceFeatures by their IDs (this will be handled in the service)

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank( message = "Name Cannot Be Blank")
    @Length(min = 4, max = 40, message = "Name can be min 4 or max 40 character long" )
    private String name;

    @NotBlank( message = "description Cannot Be Blank")
    @Length(min = 4, max = 100, message = "description can be min 4 or max 100 character long" )
    private String description;

    @NotNull
    public int capacity;

    public List<AmbulanceFeatures> getFeatures() {
        return features;
    }

    public void setFeatures(List<AmbulanceFeatures> features) {
        this.features = features;
    }

    @NotNull
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(    @NotNull int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "description Cannot Be Blank") @Length(min = 4, max = 100, message = "description can be min 4 or max 100 character long") String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name Cannot Be Blank") @Length(min = 4, max = 40, message = "Name can be min 4 or max 40 character long") String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToMany
    @JoinTable(
            name = "ambulance_type_assigned_features", // Join table name
            joinColumns = @JoinColumn(name = "ambulance_type_id"), // Column for Ambulance
            inverseJoinColumns = @JoinColumn(name = "feature_id") // Column for AmbulanceFeature
    )
    private List<AmbulanceFeatures> features;

    public AmbulanceType() {

    }



}
