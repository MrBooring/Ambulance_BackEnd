package com.rapidrescue.ambulancewale.models.enums;


public enum DriverStatus {
    AVAILABLE,
    ASSIGNED,
    EN_ROUTE_TO_PICKUP,
    AT_PICKUP_LOCATION,
    IN_TRANSIT,
    COMPLETED,
    UNAVAILABLE,
    OFFLINE,
    MAINTENANCE,
    EMERGENCY,
    AWAITING_APPROVAL,
    SUSPENDED,
}
/*Driver Status Examples

Available:                   The driver is ready to accept new requests.
On-Duty:                     The driver is currently active and logged into the system but not assigned to a request.
Assigned:                    The driver has been assigned to a specific ambulance request but hasn't started the trip yet.
En Route to Pickup:          The driver is on the way to the user's pickup location.
At Pickup Location:          The driver has reached the user's pickup location and is waiting to start the trip.
In Transit:                  The driver is currently transporting the user to the destination.
Completed:                   The trip has been completed successfully.
Unavailable:                 The driver is not available to accept new requests (e.g., logged out, on a break).
Offline:                     The driver is not logged into the system or app.
Maintenance:                 The driver's ambulance is undergoing maintenance and is temporarily unavailable.
Emergency:                   The driver is dealing with an emergency and cannot accept new requests.
Awaiting Approval:           The driver has registered but is awaiting admin approval to start accepting requests.
Suspended:                   The driver's account is temporarily disabled due to violations or other reasons.

 */