package com.rapidrescue.ambulancewale.repository;

import com.rapidrescue.ambulancewale.models.entity.Booking;
import com.rapidrescue.ambulancewale.models.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByBookingStatusAndBookingTimeBefore(BookingStatus bookingStatus, LocalDateTime cutoff);
}
