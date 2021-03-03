package com.linn.web;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class UserPrincipal implements OAuth2User {
    private String name;
    private Map<String,Object> attributes;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal() {
        this.name = "108474602032107161632";
        this.attributes = new HashMap<String, Object>();
        //attributes.put("name","Δημήτρης Σιδηρόπουλος");
        this.authorities = List.of(new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority("https://www.googleapis.com/auth/drive.install"));;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getName() {
        return String.valueOf(this.name);
    }
}
