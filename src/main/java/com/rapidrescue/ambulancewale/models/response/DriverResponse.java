package com.rapidrescue.ambulancewale.models.response;

import com.rapidrescue.ambulancewale.models.entity.Ambulance;
import com.rapidrescue.ambulancewale.models.entity.User;
import com.rapidrescue.ambulancewale.models.enums.DriverStatus;

public class DriverResponse {
    private Long driverId;

    private double lat;
    private double lng;
    private DriverStatus driverStatus;
    private Ambulance ambulance;
    private User user;

    public DriverResponse(Long driverId, double lat, double lng, DriverStatus driverStatus, Ambulance ambulance, User user) {

        this.driverId = driverId;
        this.lat = lat;
        this.lng = lng;
        this.driverStatus = driverStatus;
        this.ambulance = ambulance;
        this.user = user;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public DriverStatus getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(DriverStatus driverStatus) {
        this.driverStatus = driverStatus;
    }

    public Ambulance getAmbulance() {
        return ambulance;
    }

    public void setAmbulance(Ambulance ambulance) {
        this.ambulance = ambulance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "DriverLoginResponse{" +
                "driverId=" + driverId +
                ", lat=" + lat +
                ", lng=" + lng +
                ", driverStatus=" + driverStatus +
                ", ambulance=" + ambulance +
                ", user=" + user +
                '}';
    }
}
