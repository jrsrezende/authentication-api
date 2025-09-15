package br.com.jrsr.authenticationapi.exceptions;

public class EmailAlreadyRegisteredException extends RuntimeException {
    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
}
