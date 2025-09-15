package br.com.jrsr.authenticationapi.handlers;

import br.com.jrsr.authenticationapi.exceptions.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AccessDeniedExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e) {

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("timestamp", LocalDateTime.now());
        body.put("error", e.getMessage());

        return ResponseEntity.status(401).body(body);
    }
}

