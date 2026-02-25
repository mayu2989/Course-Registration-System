package com.mayu298.courseregistrationsystem.exception;

public class CourseDeletionNotAllowedException extends RuntimeException {
    public CourseDeletionNotAllowedException(String message) {
        super(message);
    }
}