package org.kayteam.licenses.repositories;

import org.kayteam.licenses.entities.TokenJWT;
import org.kayteam.licenses.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<TokenJWT, Long> {

    TokenJWT findByToken(String token);

    @Query("""
        SELECT t FROM TokenJWT t INNER JOIN t.relatedUser u
        WHERE u.id = :#{#user.getId()} AND (t.revoked = false)
        """)
    List<TokenJWT> findAllValidTokenByUser(@Param("user") User user);
}
