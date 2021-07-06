package ru.itpark.auth.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import ru.itpark.auth.configuration.userdetails.AuthorizationUserDetailsService;

import java.util.Collections;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthorizationUserDetailsService authorizationUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAfter(preAuthenticationFilter(), RequestHeaderAuthenticationFilter.class)
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable();
    }

    private RequestHeaderAuthenticationFilter preAuthenticationFilter() {
        RequestHeaderAuthenticationFilter preAuthenticationFilter = new RequestHeaderAuthenticationFilter();
        preAuthenticationFilter.setPrincipalRequestHeader(AUTHORIZATION);
        preAuthenticationFilter.setAuthenticationManager(authenticationManager());
        preAuthenticationFilter.setExceptionIfHeaderMissing(false);
        return preAuthenticationFilter;
    }

    @Override
    protected AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(authenticationProvider()));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        PreAuthenticatedAuthenticationProvider authenticationProvider = new PreAuthenticatedAuthenticationProvider();
        authenticationProvider.setPreAuthenticatedUserDetailsService(authorizationUserDetailsService);
        authenticationProvider.setThrowExceptionWhenTokenRejected(false);
        return authenticationProvider;
    }

}
