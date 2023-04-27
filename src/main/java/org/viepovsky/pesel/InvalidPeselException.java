package org.viepovsky.pesel;

class InvalidPeselException extends Exception {
    InvalidPeselException(String message) {
        super(message);
    }
}
