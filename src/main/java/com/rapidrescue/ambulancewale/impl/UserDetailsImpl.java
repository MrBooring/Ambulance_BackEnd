package com.rapidrescue.ambulancewale.impl;

import com.rapidrescue.ambulancewale.controller.AuthController;
import com.rapidrescue.ambulancewale.models.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public UserDetailsImpl() {
        super();
    }

    public UserDetailsImpl(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        log.info("****Inside UserDetailsImpl build method***");
        List<GrantedAuthority> authorities=new LinkedList<>();
        log.info(" After authoritiesList-------");
        user.getRole();
        log.info("user role in userServiceImpl----"+user.getRole());
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
        log.info("Authorities-----"+authorities);
        log.info(" Before build of UserDetailsImpl");
        return new UserDetailsImpl(user.getUserId()+"", user.getPassword(),authorities);
    }

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
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
