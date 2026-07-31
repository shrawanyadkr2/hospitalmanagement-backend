package com.shrawan.hospitalmanagement.util;

public class AppConstants {

    public static final String AUTH = "/api/auth";
    public static final String PATIENT = "/api/patients";
    public static final String DOCTOR = "/api/doctors";
    public static final String APPOINTMENT = "/api/appointments";
    public static final String DEPARTMENT = "/api/departments";
    public static final String PRESCRIPTION = "/api/prescriptions";
    public static final String MEDICAL_RECORD = "/api/medical-records";
    public static final String BILL = "/api/bills";
    public static final String PAYMENT = "/api/payments";
    public static final String ADMIN = "/api/admin";
    public static final String DASHBOARD = "/api/dashboard";
    public static final String FILE = "/api/files";

    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String VERIFY_EMAIL = "/verify-email";
    public static final String FORGOT_PASSWORD = "/forgot-password";
    public static final String RESET_PASSWORD = "/reset-password";
    public static final String PROFILE = "/profile";
    public static final String ID = "/{id}";

    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";

    private AppConstants() {
        // Private constructor to prevent instantiation
    }
}
