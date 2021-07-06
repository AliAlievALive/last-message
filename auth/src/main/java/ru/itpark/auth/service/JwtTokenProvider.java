package ru.itpark.auth.service;

import com.nimbusds.jwt.JWTClaimsSet;
import ru.itpark.auth.dto.User;

public interface JwtTokenProvider {

    String generateToken(User user);

    boolean validateToken(String token);

    JWTClaimsSet parseToken(String token);

}
