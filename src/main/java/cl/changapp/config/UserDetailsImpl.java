package cl.changapp.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cl.changapp.entity.related.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Si más adelante agregas roles, puedes retornar una lista de authorities aquí
    	return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // puedes personalizarlo si implementas lógica de expiración
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // puedes agregar bloqueo si lo necesitas
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // puedes personalizarlo también
    }

    @Override
    public boolean isEnabled() {
        return true; // útil si manejas usuarios deshabilitados
    }
}
