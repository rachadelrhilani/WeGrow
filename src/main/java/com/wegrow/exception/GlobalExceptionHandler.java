package com.wegrow.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Ressource non trouvée -> Code HTTP 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // 2. Conflit métier (Doublon ou Suppression impossible) -> Code HTTP 409
    @ExceptionHandler({ResourceAlreadyExistsException.class, ResourceConflictException.class})
    public ResponseEntity<Map<String, String>> handleConflict(RuntimeException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // 3. Conflit de concurrence (Optimistic Lock) ou contrainte SQL BDD -> Code HTTP 409
    @ExceptionHandler({DataIntegrityViolationException.class, ObjectOptimisticLockingFailureException.class})
    public ResponseEntity<Map<String, String>> handleDataIntegrityAndConcurrency(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Un conflit de données ou de concurrence s'est produit. Veuillez réessayer.");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // 4. Erreurs génériques (Fallback) -> Code HTTP 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Une erreur interne s'est produite : " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}