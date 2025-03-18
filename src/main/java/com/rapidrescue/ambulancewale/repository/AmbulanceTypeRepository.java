package com.rapidrescue.ambulancewale.repository;

import com.rapidrescue.ambulancewale.models.entity.AmbulanceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmbulanceTypeRepository extends JpaRepository<AmbulanceType, Long> {
}
