package br.com.jrsr.authenticationapi.handlers;

import br.com.jrsr.authenticationapi.exceptions.EmailAlreadyRegisteredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class EmailAlreadyRegisteredExceptionHandler {

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<Map<String, Object>> handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException e){

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("timestamp", LocalDateTime.now());
        body.put("message", e.getMessage());

        return ResponseEntity.status(409).body(body);
    }
}
