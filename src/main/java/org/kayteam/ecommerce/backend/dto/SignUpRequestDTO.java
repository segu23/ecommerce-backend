package org.kayteam.licenses.models;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDTO {

    @Email
    private String email;

    private String password;

    private String userName;
}
