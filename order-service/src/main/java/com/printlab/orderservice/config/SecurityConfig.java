//package com.printlab.orderservice.config;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//import java.util.List;
//
//
//@Configuration
//@EnableMethodSecurity
//public class SecurityConfig {
//
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private final JsonSecurityHandlers jsonSecurityHandlers;
//    private final OAuth2UserService oAuth2UserService;
//    private final OAuth2SecurityHandler oAuth2SecurityHandler;
//    private final OAuth2FailureHandler oAuth2FailureHandler;
//
//
//    @Value("${app.cors.allowed-origins}")
//    private String allowedOrigins;
//
//    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
//                          JsonSecurityHandlers jsonSecurityHandlers,
//                          OAuth2UserService oAuth2UserService,
//                          OAuth2SecurityHandler oAuth2SecurityHandler,
//                          OAuth2FailureHandler oAuth2FailureHandler) {
//        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//        this.jsonSecurityHandlers = jsonSecurityHandlers;
//        this.oAuth2UserService = oAuth2UserService;
//        this.oAuth2SecurityHandler = oAuth2SecurityHandler;
//        this.oAuth2FailureHandler = oAuth2FailureHandler;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        List<String> origins = Arrays.stream(allowedOrigins.split(","))
//                .map(String::trim)
//                .filter(s -> !s.isEmpty())
//                .toList();
//
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOrigins(origins);
//        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//        config.setAllowedHeaders(List.of("*"));
//        config.setAllowCredentials(true);
//        config.setMaxAge(3600L);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(Customizer.withDefaults())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
//
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
//
//                        .requestMatchers("/oauth2/**", "/login/**").permitAll()
//                        .anyRequest().authenticated())
//
//                .oauth2Login(oauth2 -> oauth2
//                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
//                        .successHandler(oAuth2SecurityHandler)
//                        .failureHandler(oAuth2FailureHandler)
//                )
//
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint(jsonSecurityHandlers)
//                        .accessDeniedHandler(jsonSecurityHandlers))
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//}