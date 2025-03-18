package com.rapidrescue.ambulancewale.repository;

import com.rapidrescue.ambulancewale.models.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByPhone(String phone);
}
