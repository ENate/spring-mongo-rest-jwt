package com.minejava.springjwt.document;

import java.util.Collection;
import java.util.Collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NonNull;

@Document
@Data
public class User implements UserDetails {

    @Id
    private String id;
    @Indexed(unique = true)

    @NonNull
    private String username;
    @Indexed(unique = true)

    @NonNull
    private String email;
    @JsonIgnore

    @NonNull
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // DO Auto-generated method stub
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        // DO Auto-generated method stub
        return password;
    }

    @Override
    public String getUsername() {
        // DO Auto-generated method stub
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // DO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // DO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()  {
        // DO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // DO Auto-generated method stub
        return true;
    }
    
}
