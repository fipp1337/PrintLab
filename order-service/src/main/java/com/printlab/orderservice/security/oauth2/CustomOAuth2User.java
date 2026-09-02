//package com.printlab.orderservice.security.oauth2;
//
//import lombok.Getter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//import java.util.Collection;
//import java.util.Map;
//
//@Getter
//public class CustomOAuth2User implements OAuth2User {
//    private final OAuth2User delegate;
//    private final AdminUser adminUser;
//    private final Family family;
//
//    public CustomOAuth2User(OAuth2User delegate, AdminUser adminUser, Family family) {
//        this.delegate = delegate;
//        this.adminUser = adminUser;
//        this.family = family;
//    }
//
//    public boolean isAdmin() {
//        return adminUser != null;
//    }
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        return delegate.getAttributes();
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return delegate.getAuthorities();
//    }
//
//    @Override
//    public String getName() {
//        return delegate.getName();
//    }
//}
