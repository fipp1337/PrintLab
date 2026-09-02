//package com.printlab.orderservice.security.oauth2;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import tools.jackson.databind.ObjectMapper;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//import java.util.Map;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class OAuth2SecurityHandler extends SimpleUrlAuthenticationSuccessHandler {
//    private final JwtService jwtService;
//    private final ObjectMapper objectMapper;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication) throws IOException {
//        CustomOAuth2User customUser = (CustomOAuth2User) authentication.getPrincipal();
//
//        String accessToken;
//        String refreshToken;
//
//        if (customUser.isAdmin()) {
//            log.info("Generating JWT tokens for AdminUser [ID: {}, Email: {}]",
//                    customUser.getAdminUser().getId(), customUser.getAdminUser().getEmail());
//            accessToken = jwtService.generateAdminToken(customUser.getAdminUser());
//            refreshToken = jwtService.generateAdminRefreshToken(customUser.getAdminUser());
//        } else {
//            log.info("Generating JWT tokens for Family [ID: {}, Email: {}]",
//                    customUser.getFamily().getId(), customUser.getFamily().getEmail());
//            accessToken = jwtService.generateToken(customUser.getFamily(), List.of(Role.USER));
//            refreshToken = jwtService.generateRefreshToken(customUser.getFamily(), List.of(Role.USER));
//        }
//
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
//
//
//        Map<String, String> tokens = Map.of(
//                "accessToken", accessToken,
//                "refreshToken", refreshToken
//        );
//
//        objectMapper.writeValue(response.getWriter(), tokens);
//        log.debug("Successfully returned OAuth2 JWT tokens response for client IP: [{}]", request.getRemoteAddr());
//    }
//}
