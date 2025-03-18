package com.rapidrescue.ambulancewale.models.request;

public class AmbulanceBookingRequest {
    private String phoneNumber;
    private double latitude;
    private double longitude;

    // Constructors
    public AmbulanceBookingRequest() {}

    // Getters and Setters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


}
