package com.arka.user_service.domain.util;

public class ExceptionMessages {

    private ExceptionMessages() {
    }

    public static final String USER_NOT_FOUND = "User not found";
    public static final String ROLE_NOT_FOUND = "Role not found";
    public static final String INVALID_EMAIL_FORMAT = "Invalid email format";
    public static final String PASSWORD_TOO_SHORT = "Password must be at least 8 characters long";
    public static final String USERNAME_ALREADY_EXISTS = "Username already exists";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists";
    public static final String INVALID_USER_ID = "Invalid user ID provided";
    public static final String PASSWORDS_DO_NOT_MATCH = "Passwords do not match";
    public static final String INVALID_FULLNAME_FORMAT = "Invalid full name format";
    public static final String INVALID_ADDRESS_FORMAT = "Invalid address format";
    public static final String INVALID_PHONE_FORMAT = "Invalid phone format";
}
