package com.wegrow.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.HashMap;
import java.util.Map;

public class GlobalExceptionHandler {

    public static void handle(HttpServletResponse res, ObjectMapper json, Exception e) throws Exception {
        if (e instanceof ResourceNotFoundException) {
            writeError(res, json, 404, e.getMessage());
        } else if (e instanceof ResourceAlreadyExistsException || e instanceof ResourceConflictException) {
            writeError(res, json, 409, e.getMessage());
        } else if (e instanceof DataIntegrityViolationException
                || e instanceof ObjectOptimisticLockingFailureException) {
            writeError(res, json, 409, "Conflit de données, veuillez réessayer.");
        } else {
            writeError(res, json, 500, "Erreur interne : " + e.getMessage());
        }
    }

    public static void writeError(HttpServletResponse res, ObjectMapper json, int status, String message)
            throws Exception {
        Map<String, String> body = new HashMap<>();
        body.put("error", message);
        res.setStatus(status);
        json.writeValue(res.getWriter(), body);
    }
}
