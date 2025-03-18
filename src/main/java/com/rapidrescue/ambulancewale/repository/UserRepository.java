package com.rapidrescue.ambulancewale.repository;

import com.rapidrescue.ambulancewale.models.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(@Email String email);

    boolean existsByPhone(@NotBlank(message = "Phone number cannot be blank") String phoneNumber);

    User findByPhone(String phoneNumber);

    Optional<User>  findByUserId(Long userId);
}
