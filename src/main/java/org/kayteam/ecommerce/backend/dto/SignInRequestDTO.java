package org.kayteam.ecommerce.backend.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequestDTO extends TwoFactorRequestDTO {

    @Email
    private String email;

    private String password;
}
