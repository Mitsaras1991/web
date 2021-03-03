package com.linn.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class Custom0auth2UserService extends DefaultOAuth2UserService {
    private static final Logger logger = LoggerFactory.getLogger(Custom0auth2UserService.class);
    @Autowired
    private UserRepository userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        logger.info("Load User Request %s");
        OAuth2User user = super.loadUser(userRequest);
        System.out.println(userRequest.getClientRegistration().getClientId());
        System.out.println(user.getAttributes());

        //processOAuth2User(userRequest,user);
        return user;//processOAuth2User(userRequest,user);

    }
}
