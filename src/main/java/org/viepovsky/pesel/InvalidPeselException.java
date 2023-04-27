package org.viepovsky.pesel;

public class InvalidPeselException extends Exception {
    InvalidPeselException(String message) {
        super(message);
    }
}
