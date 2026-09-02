//package com.printlab.orderservice.security.oauth2;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.OAuth2Error;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class OAuth2UserService extends DefaultOAuth2UserService {
//
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
//        String provider = request.getClientRegistration().getRegistrationId();
//        log.info("Processing OAuth2 login request from provider: [{}]", provider);
//
//        OAuth2User oAuth2User = super.loadUser(request);
//
//        String email = oAuth2User.getAttribute("email");
//        if (email == null) {
//            OAuth2Error error = new OAuth2Error("email_not_found", "Email not found from Google provider", null);
//            throw new OAuth2AuthenticationException(error, error.getDescription());
//        }
//
//        String normalizedEmail = email.trim().toLowerCase();
//
//        Optional<AdminUser> adminUser = adminUserRepository.findByEmailIgnoreCase(normalizedEmail);
//        if (adminUser.isPresent()) {
//            log.info("OAuth2 user matched AdminUser with email: [{}]", normalizedEmail);
//            return new CustomOAuth2User(oAuth2User, adminUser.get(), null);
//        }
//
//        Optional<Family> family = familyRepository.findByEmailIgnoreCase(normalizedEmail);
//        if (family.isPresent()) {
//            log.info("OAuth2 user matched Family with email: [{}]", normalizedEmail);
//            return new CustomOAuth2User(oAuth2User, null, family.get());
//        }
//
//        OAuth2Error error = new OAuth2Error("user_not_found", "User with email " + normalizedEmail + " not found", null);
//        throw new OAuth2AuthenticationException(error, error.getDescription());
//    }
//}
