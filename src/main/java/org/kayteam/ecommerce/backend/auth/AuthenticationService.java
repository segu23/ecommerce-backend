package org.kayteam.ecommerce.backend.auth;

import org.jboss.aerogear.security.otp.Totp;
import org.kayteam.ecommerce.backend.dto.SignInRequestDTO;
import org.kayteam.ecommerce.backend.dto.TwoFactorRequestDTO;
import org.kayteam.ecommerce.backend.exceptions.InvalidTwoFactorCodeException;
import org.kayteam.ecommerce.backend.exceptions.TwoFactorRequiredException;
import org.kayteam.ecommerce.backend.models.TokenJWT;
import org.kayteam.ecommerce.backend.models.User;
import org.kayteam.ecommerce.backend.services.TokenService;
import org.kayteam.ecommerce.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Map<String, Object> authenticate(SignInRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Map<String, Object> res = new HashMap<>();
        User user = userService.findByEmail(request.getEmail());

        if (user.getTwoFactorEnabled()) {
            checkUserTwoFactor(user, request);
        }

        String generatedToken = generateJwtToken(user);

        res.put("user", dataMapper.getUserDAO(user));
        res.put("token", generatedToken);

        return res;
    }

    public void checkUserTwoFactor(User user, TwoFactorRequestDTO twoFactorRequestDTO) {
        if (twoFactorRequestDTO.getTwoFactorCode() == null) {
            throw new TwoFactorRequiredException();
        } else {
            if (!verifyOTPCode(user.getSecretTwoFactorCode(), twoFactorRequestDTO.getTwoFactorCode())) {
                throw new InvalidTwoFactorCodeException();
            }
        }
    }

    public String generateJwtToken(User user) {
        String jwtToken = jwtService.generateToken(user);

        TokenJWT token = new TokenJWT();
        token.setRelatedUser(user);
        token.setToken(jwtToken);
        tokenService.save(token);

        return jwtToken;
    }

    public void revokeAllUserTokens(User user) {
        List<TokenJWT> validUserTokens = tokenService.findAllValidTokenByUser(user);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setRevoked(true);
            tokenService.save(token);
        });
    }

    public boolean verifyOTPCode(String userSecretCode, String twoFactorCode) {
        Totp totp = new Totp(userSecretCode);
        return isValidOTPCodeSize(twoFactorCode) && totp.verify(twoFactorCode);
    }

    private boolean isValidOTPCodeSize(String code) {
        try {
            Long.parseLong(code);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
