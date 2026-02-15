package com.mayu298.courseregistrationsystem.exception;

public class DuplicateEnrollmentException extends RuntimeException{
    public DuplicateEnrollmentException(String message)
    {
        super(message);
    }
}
