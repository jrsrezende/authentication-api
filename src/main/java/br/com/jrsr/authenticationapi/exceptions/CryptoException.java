package br.com.jrsr.authenticationapi.exceptions;

public class CryptoException extends RuntimeException {
    public CryptoException(String message, Throwable cause) {
        super(message, cause);
    }
}
