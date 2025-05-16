package com.supron.tourfound.exception;

public class TourNotFoundException extends RuntimeException {

    public TourNotFoundException() {
        super();
    }

    public TourNotFoundException(String message) {
        super(message);
    }
}
