package com.linn.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OpenIdService extends OidcUserService {
    private  static final Logger logger = LoggerFactory.getLogger(OpenIdService.class);
    @Autowired
    private UserRepository userRepository;
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        logger.info("openid");
        processUser(oidcUser);
        return super.loadUser(userRequest);
    }

    private void processUser(OidcUser oidcUser) {
        Optional<User> user= userRepository.findByEmail(oidcUser.getAttribute("email"));
        if(!user.isPresent()){
            System.out.println("NEW USER");
            User userRegister=new User();
            userRegister.setEmail(oidcUser.getAttribute("email"));
            userRegister.setName(oidcUser.getAttribute("name"));
            userRegister.setPicture(oidcUser.getAttribute("picture"));
            userRegister.setSub(oidcUser.getAttribute("sub"));
            userRepository.save(userRegister);
        }
    }


}