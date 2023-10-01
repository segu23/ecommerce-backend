package org.kayteam.licenses.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tokens")
public class TokenJWT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
    private Boolean revoked = false;

    @ManyToOne
    private User relatedUser;
}
