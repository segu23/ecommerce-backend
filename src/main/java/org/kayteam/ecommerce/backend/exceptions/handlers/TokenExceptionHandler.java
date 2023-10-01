package org.kayteam.ecommerce.backend.exceptions.handlers;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletResponse;
import org.kayteam.licenses.exceptions.ExpiredTokenException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TokenExceptionHandler {

    @ExceptionHandler({ExpiredTokenException.class, MalformedJwtException.class, ExpiredJwtException.class})
    public ResponseEntity<Map<String, String>> handleJWTException(ExpiredTokenException e) {
        Map<String, String> res = new HashMap<>();

        res.put("error", "INVALID_TOKEN");
        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(res);
    }
}