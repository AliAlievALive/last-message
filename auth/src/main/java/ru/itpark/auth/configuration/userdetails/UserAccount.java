package ru.itpark.auth.configuration.userdetails;

import lombok.*;
import lombok.experimental.Wither;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itpark.auth.dto.Role;

import java.util.Collection;
import java.util.UUID;

@Getter
@With
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserAccount implements UserDetails {

    private UUID id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String login;
    private String password;
    private Role role;
    private String token;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
