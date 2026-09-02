package com.printlab.orderservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AppErrorCode {

    // Auth / security
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "Wrong password: %s"),
    WRONG_EMAIL(HttpStatus.UNAUTHORIZED, "Wrong email: %s"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid email or password"),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "Invalid token"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "Access denied"),
    INVALID_INVITE_CODE(HttpStatus.UNAUTHORIZED, "Invalid invite code"),
    INVALID_VERIFICATION_CODE(HttpStatus.UNAUTHORIZED, "Invalid verification code"),
    REGISTRATION_NOT_INITIATED(HttpStatus.UNAUTHORIZED, "Registration not initiated"),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "Email already exists"),
    ADDRESS_ALREADY_EXISTS(HttpStatus.CONFLICT, "Address already exists"),
    PASSWORDS_DO_NOT_MATCH(HttpStatus.BAD_REQUEST, "Passwords do not match"),
    OLD_PASSWORD_REQUIRED(HttpStatus.CONFLICT, "Old password required"),
    INVALID_OLD_PASSWORD(HttpStatus.CONFLICT, "Invalid old password"),
    NEW_PASSWORD_REQUIRED(HttpStatus.CONFLICT, "New password required"),
    NEW_PASSWORD_MATCH_WITH_OLD(HttpStatus.CONFLICT, "New password match with old"),
    INVALID_PHONE_FORMAT(HttpStatus.CONFLICT, "Invalid phone number format"),
    REGISTRATION_SESSION_EXPIRED(HttpStatus.CONFLICT, "Registration session expired"),
    SESSION_EXPIRED(HttpStatus.CONFLICT, "Session expired"),
    RESET_SESSION_EXPIRED(HttpStatus.CONFLICT, "Reset session expired"),
    OTP_TOO_MANY_REQUESTS(HttpStatus.CONFLICT, "Too many one-time password requests"),
    CONFIRM_PASSWORD_REQUIRED(HttpStatus.CONFLICT, "Confirm password required"),

    // Family / users / admin
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found: %s"),
    FAMILY_NOT_FOUND(HttpStatus.NOT_FOUND, "Family not found: %s"),
    INVALID_FAMILY(HttpStatus.BAD_REQUEST, "Invalid family"),
    ROLES_EMPTY(HttpStatus.BAD_REQUEST, "Roles must not be empty"),

    // Pool
    POOL_SESSION_NOT_FOUND(HttpStatus.NOT_FOUND, "Pool session not found: %s"),
    POOL_SESSION_NOT_AVAILABLE(HttpStatus.CONFLICT, "Pool session not available: %s"),
    POOL_SESSION_IS_FULL(HttpStatus.CONFLICT, "Pool session is full: %s"),
    POOL_HAS_BEEN_BOOKED(HttpStatus.BAD_REQUEST, "Pool has been booked: %s"),
    NO_POOL_TEMPLATES_FOUND(HttpStatus.NOT_FOUND, "No pool templates found"),
    OUT_OF_TICKETS(HttpStatus.NOT_ACCEPTABLE, "Not enough tickets"),
    INVALID_DATES(HttpStatus.CONFLICT, "Not valid dates"),

    // Cinema
    CINEMA_SESSION_NOT_FOUND(HttpStatus.NOT_FOUND, "Cinema session not found %s"),
    CINEMA_SESSION_IS_FULL(HttpStatus.CONFLICT, "Cinema session is full: %s"),
    HALL_NOT_FOUND(HttpStatus.NOT_FOUND, "Hall not found"),
    SEAT_NOT_FOUND(HttpStatus.NOT_FOUND, "Cinema seat not found %s"),
    SEAT_ALREADY_BOOKED(HttpStatus.BAD_REQUEST, "Seat has been booked: %s"),
    TOO_MANY_SEATS(HttpStatus.CONFLICT, "You can't book more seats, than there are members in your family"),
    POSTER_NOT_FOUND(HttpStatus.NOT_FOUND, "Poster not found for session: %s"),
    PROFILE_PICTURE_NOT_FOUND(HttpStatus.NOT_FOUND, "Profile picture not found"),

    // Shared booking
    BOOKING_NOT_FOUND(HttpStatus.NOT_FOUND, "Booking not found %s"),
    BOOKING_ALREADY_CANCELLED(HttpStatus.CONFLICT, "Booking already has been canceled"),
    BOOKING_NOT_CANCELLED(HttpStatus.CONFLICT, "Booking has not been canceled: %s"),
    NOT_YOUR_BOOKING(HttpStatus.NOT_ACCEPTABLE, "Not your booking"),
    SESSION_ALREADY_STARTED(HttpStatus.CONFLICT, "Session was started"),
    USERS_NOT_FROM_SAME_FAMILY(HttpStatus.CONFLICT, "User not from same family"),

    // Facilities / activities / files
    FACILITY_NOT_FOUND(HttpStatus.NOT_FOUND, "Facility not found: %s"),
    FACILITY_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "Facility image not found"),
    ACTIVITY_NOT_FOUND(HttpStatus.NOT_FOUND, "Activity not found: %s"),
    ACTIVITY_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "Activity image not found"),
    FILE_UPLOAD_FAILED(HttpStatus.CONFLICT, "File upload failed"),
    FILE_DELETE_FAILED(HttpStatus.CONFLICT, "File delete failed"),
    FILE_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "File too large"),
    INVALID_FILE_FORMAT(HttpStatus.CONFLICT, "Invalid file format"),
    FILE_PROCESSING_FAILED(HttpStatus.CONFLICT, "File processing failed"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "Invalid request"),
    MISSING_ACTIVITY_DATE(HttpStatus.NOT_FOUND, "Missing activity date"),
    MISSING_ACTIVITY_DAYS(HttpStatus.NOT_FOUND, "Missing activity days");

    private final HttpStatus status;
    private final String message;

    AppErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
