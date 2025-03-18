package com.rapidrescue.ambulancewale.repository;

import com.rapidrescue.ambulancewale.models.entity.Ambulance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmbulanceRepository extends JpaRepository<Ambulance, Integer> {
}
