package org.kayteam.ecommerce.backend.exceptions.handlers;

import jakarta.servlet.http.HttpServletResponse;
import org.kayteam.licenses.exceptions.InvalidTwoFactorCodeException;
import org.kayteam.licenses.exceptions.TwoFactorRequiredException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TwoFactorExceptionHandler {

    @ExceptionHandler(TwoFactorRequiredException.class)
    public ResponseEntity<Map<String, String>> handleTwoFactorRequiredException(TwoFactorRequiredException e) {
        Map<String, String> res = new HashMap<>();

        res.put("error", "2FA_REQUIRED");

        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(res);
    }

    @ExceptionHandler(InvalidTwoFactorCodeException.class)
    public ResponseEntity<Map<String, String>> handleInvalidTwoFactorCodeException(InvalidTwoFactorCodeException e) {
        Map<String, String> res = new HashMap<>();

        res.put("error", "INVALID_2FA_CODE");
        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(res);
    }
}
