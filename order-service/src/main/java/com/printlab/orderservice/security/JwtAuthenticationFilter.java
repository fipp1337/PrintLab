//package com.printlab.orderservice.security;
//
//
//import com.printlab.orderservice.dto.ResponseError;
//import com.printlab.orderservice.exception.AppException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import tools.jackson.databind.ObjectMapper;
//
//import java.io.IOException;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtService jwtService;
//    private final ObjectMapper objectMapper;
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain) throws ServletException, IOException {
//
//        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (header != null && header.startsWith("Bearer ")) {
//            String token = header.substring(7);
//            try {
//                SecurityFamily family = jwtService.parseToken(token);
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(family, null, family.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } catch (AppException ex) {
//                // Invalid/expired token: continue as anonymous.
//                // Protected endpoints will correctly return 401 via AuthenticationEntryPoint.
//                SecurityContextHolder.clearContext();
//            } catch (Exception ex) {
//                // Unexpected error while parsing token must NOT look like "not authenticated".
//                log.error("Unexpected JWT filter error", ex);
//                SecurityContextHolder.clearContext();
//                writeInternalError(response, ex);
//                return;
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private void writeInternalError(HttpServletResponse response, Exception ex) throws IOException {
//        String message = ex.getMessage() != null && !ex.getMessage().isBlank()
//                ? ex.getMessage()
//                : ex.getClass().getSimpleName();
//        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        objectMapper.writeValue(
//                response.getOutputStream(),
//                new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), message)
//        );
//    }
//}
