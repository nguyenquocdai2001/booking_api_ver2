package meu.booking_rebuild_ver2.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import meu.booking_rebuild_ver2.model.Passanger.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@Data
public class CustomerDetailsImplement implements UserDetails {
    private Customer customer;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(customer.getUserRole()));
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return customer.getPassword();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return customer.getPhone();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
