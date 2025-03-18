package com.rapidrescue.ambulancewale.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class AmbulanceFeatures extends BaseEntity {

    public AmbulanceFeatures( String featureName,String description) {
        this.description = description;
        this.featureName = featureName;
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
    }

    public AmbulanceFeatures() {

    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName( String featureName) {
        this.featureName = featureName;
    }

    public  String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feature_id;

    @NotBlank(message = "Name Cannot Be Blank")
    @Length(min = 4, max = 40, message = "Name can be min 4 or max 40 characters long")
    private String featureName;

    @NotBlank(message = "Description Cannot Be Blank")
    @Length(min = 4, max = 100, message = "Description can be min 4 or max 100 characters long")
    private String description;


}
