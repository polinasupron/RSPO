package com.supron.tourfound.exception;

public class UsernameIsAlreadyExists extends RuntimeException {

    public UsernameIsAlreadyExists(String message) {
        super(message);
    }

    public UsernameIsAlreadyExists() {
        super();
    }
}
