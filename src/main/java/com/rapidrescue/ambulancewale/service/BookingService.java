package com.rapidrescue.ambulancewale.service;

import com.rapidrescue.ambulancewale.controller.AuthController;
import com.rapidrescue.ambulancewale.models.entity.Booking;
import com.rapidrescue.ambulancewale.models.entity.Driver;
import com.rapidrescue.ambulancewale.models.entity.User;
import com.rapidrescue.ambulancewale.models.enums.BookingStatus;
import com.rapidrescue.ambulancewale.models.enums.DriverStatus;
import com.rapidrescue.ambulancewale.models.enums.Role;
import com.rapidrescue.ambulancewale.models.request.AmbulanceBookingRequest;
import com.rapidrescue.ambulancewale.repository.AmbulanceRepository;
import com.rapidrescue.ambulancewale.repository.BookingRepository;
import com.rapidrescue.ambulancewale.repository.DriverRepository;
import com.rapidrescue.ambulancewale.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private AmbulanceRepository ambulanceRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SmsService smsService;

    private final double searchRange = 5.0; //km

    Logger log = Logger.getLogger(AuthController.class.getName());


    public Booking createBooking(AmbulanceBookingRequest request) {
    log.info("==========================Creating Booking =============================");
        Booking booking = new Booking();

        User user = userRepository.findByPhone(request.getPhoneNumber());
        if (user == null) {
            user = new User();
            user.setPhone(request.getPhoneNumber());
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setRole(Role.CUSTOMER);
            userRepository.save(user);
        }
        booking.setBookingStatus(BookingStatus.WAITING_FOR_DRIVER);
        booking.setBookingTime(LocalDateTime.now());
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());
        booking.setLatitude(request.getLatitude());
        booking.setLongitude(request.getLongitude());
        booking.setUser(user);
        // Find the nearest available driver
        List<Driver> driversInRange = findAvailableDriversWithinRange(request.getLatitude(), request.getLongitude());

        if (!driversInRange.isEmpty()) {
            Driver nearestDriver = driversInRange.get(0); // Pick the closest driver

            assignDriverToBooking(nearestDriver, booking);
        } else {
            log.info("No available drivers found. Booking remains in WAITING_FOR_DRIVER.");
            // Keep it waiting for the cron job to try again
        }
        Booking bk  = bookingRepository.save(booking);
        smsService.sendSms(   "Your ambulance booking (ID: " + bk.getBookingId() + ") has been received. Our team is assigning a driver, and they will reach you shortly. We will keep updating you. Stay safe!", booking.getUser().getPhone());
        return  bk;
    }

    private void assignDriverToBooking(Driver driver, Booking booking) {
        booking.setDriver(driver);
        booking.setBookingStatus(BookingStatus.CONFIRMED );
        driver.setDriverStatus(DriverStatus.EN_ROUTE_TO_PICKUP);
        driverRepository.save(driver);
        bookingRepository.save(booking);
        smsService.sendSms("EMERGENCY ASSISTANCE REQUIRED!\n (ID: " + booking.getBookingId() + ") Open the Ambulancewale driver app for location ", booking.getDriver().getPhone());
        smsService.sendSms("Your booking (ID: " + booking.getBookingId() + ") An ambulance is on the way Track your ambulance via [URL], We will keep updating you. Stay safe!", booking.getUser().getPhone());
    }


    public List<Driver> findAvailableDriversWithinRange(double userLat, double userLong) {
        List<Driver> drivers = driverRepository.findByDriverStatus(DriverStatus.AVAILABLE);
        log.info("================================ Driver Found =============================\n"+ drivers.size() );
        return drivers.stream()
                .sorted(Comparator.comparingDouble(driver -> calculateDistance(userLat, userLong, driver.getLat(), driver.getLng())))
                .limit(1) // Fetch only the nearest driver
                .collect(Collectors.toList());
    }







    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        log.info("================================ Driver Distance =============================\n"+R*c );
        return R * c; // Convert to kilometers

    }



    @Scheduled(fixedRate = 60000) // Runs every minute
    public void checkPendingBookings() {
        log.info("============================= checkPendingBookings cron job =============================");

        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(1);
        List<Booking> pendingBookings = bookingRepository.findByBookingStatusAndBookingTimeBefore(BookingStatus.WAITING_FOR_DRIVER, cutoff);

        for (Booking booking : pendingBookings) {
            List<Driver> driversInRange = findAvailableDriversWithinRange(booking.getLatitude(), booking.getLongitude());

            if (!driversInRange.isEmpty()) {
                Driver nearestDriver = driversInRange.get(0); // Pick the closest driver
                assignDriverToBooking(nearestDriver, booking);


            } else {
                log.info("No drivers found for Booking ID: " + booking.getBookingId());

                // Check if it's time to cancel (retrying for 5 minutes)
                if (Duration.between(booking.getBookingTime(), LocalDateTime.now()).toMinutes() >= 5) {
                    booking.setBookingStatus(BookingStatus.CANCELLED_NO_DRIVER_AVAILABLE);
                    log.info("Booking ID " + booking.getBookingId() + " cancelled due to no available drivers.");
                    smsService.sendSms("Your booking (ID: " + booking.getBookingId() + ") We Could not find any ambulance active nearby We will call you to assist you shortly", booking.getUser().getPhone());

                }
            }
            bookingRepository.save(booking);
        }
    }

}
