package org.kayteam.ecommerce.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TwoFactorRequiredException extends RuntimeException {
}
