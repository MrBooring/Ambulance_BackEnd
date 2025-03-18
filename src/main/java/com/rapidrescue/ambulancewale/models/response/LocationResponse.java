package com.rapidrescue.ambulancewale.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class LocationResponse {
    private double latitude;
    private double longitude;

    public LocationResponse(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
