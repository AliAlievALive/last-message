package ru.itpark.auth.service.implementation;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itpark.auth.dto.User;
import ru.itpark.auth.exception.AuthTokenGenerateException;
import ru.itpark.auth.exception.AuthTokenParseException;
import ru.itpark.auth.service.JwtTokenProvider;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenProviderImpl implements JwtTokenProvider {

    @Value("${jwt.expiration.hours}")
    private long expirationInHours;
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public String generateToken(User user) {
        try {
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .expirationTime(Date.from(LocalDateTime.now()
                            .plusHours(expirationInHours)
                            .atZone(ZoneId.systemDefault())
                            .toInstant()))
                    .issueTime(new Date())
                    .claim("id", user.getId().toString())
                    .claim("role", user.getRole())
                    .claim("login", user.getLogin())
                    .claim("firstName", user.getFirstName())
                    .claim("lastName", user.getLastName())
                    .claim("middleName", user.getMiddleName())
                    .build();
            SignedJWT signed = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
            signed.sign(new MACSigner(jwtSecret));
            return signed.serialize();
        } catch (JOSEException e) {
            throw new AuthTokenGenerateException("Token generate fail");
        }
    }

    @Override
    public boolean validateToken(String token) {
        JWTClaimsSet claims = parseToken(token);
        return new Date(System.currentTimeMillis()).before(claims.getExpirationTime());
    }

    @Override
    public JWTClaimsSet parseToken(String token) {
        try {
            SignedJWT deserialized = SignedJWT.parse(token);
            MACVerifier verifier = new MACVerifier(jwtSecret);
            if (deserialized.verify(verifier)) {
                return deserialized.getJWTClaimsSet();
            }
            throw new AuthTokenParseException("Incorrect token");
        } catch (ParseException | JOSEException e) {
            throw new AuthTokenParseException("Can not parse token", e);
        }

    }

}
