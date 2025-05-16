package com.supron.tourfound.exception;

public class IncorrectPhoneException extends RuntimeException {
    public IncorrectPhoneException() {
        super();
    }

    public IncorrectPhoneException(String message) {
        super(message);
    }
}
