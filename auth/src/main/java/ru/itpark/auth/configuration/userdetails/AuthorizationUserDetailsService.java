package ru.itpark.auth.configuration.userdetails;

import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.itpark.auth.dto.Role;
import ru.itpark.auth.exception.AuthException;
import ru.itpark.auth.service.JwtTokenProvider;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthorizationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    public static final String BEARER = "Bearer";
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken) {
        String authorizationHeader = preAuthenticatedAuthenticationToken.getPrincipal().toString();
        if (!authorizationHeader.startsWith(BEARER)) {
            throw new AuthException("Invalid authentication scheme found in Authorization header");
        }
        String token = authorizationHeader.replace(BEARER, "").trim();
        if (StringUtils.isBlank(token)) {
            throw new AuthException("Authorization header token is empty");
        }
        return loadUserDetails(token);
    }

    private UserDetails loadUserDetails(String token) {
            return Optional.ofNullable(jwtTokenProvider.parseToken(token))
                    .map(JWTClaimsSet::getClaims)
                    .map(claims -> new UserAccount()
                            .withId(UUID.fromString(claims.get("id").toString()))
                            .withFirstName(claims.get("firstName").toString())
                            .withLastName(claims.get("lastName").toString())
                            .withMiddleName(claims.get("middleName").toString())
                            .withLogin(claims.get("login").toString())
                            .withPassword(StringUtils.EMPTY)
                            .withRole(Role.valueOf(claims.get("role").toString()))
                            .withToken(token)
                            .withAccountNonExpired(true)
                            .withAccountNonLocked(true)
                            .withCredentialsNonExpired(true)
                            .withEnabled(true)
                            .withAuthorities(List.of(
                                    new SimpleGrantedAuthority(String.format("ROLE_%s",
                                            claims.get("role").toString())))))
                    .orElseThrow(() -> new UsernameNotFoundException("Unknown user by token " + token));
    }

}
