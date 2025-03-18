package com.rapidrescue.ambulancewale.repository;

import com.rapidrescue.ambulancewale.models.entity.AmbulanceFeatures;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmbulanceFeatureRepository extends JpaRepository<AmbulanceFeatures, Long> {
}