package com.printlab.orderservice.security.oauth2;

import com.printlab.orderservice.dto.ResponseError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2FailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String message = exception.getMessage();
        if (exception instanceof OAuth2AuthenticationException oauthEx && oauthEx.getError() != null) {
            message = oauthEx.getError().getDescription();
        }

        log.warn("OAuth2 authentication failure from IP [{}]: {}", request.getRemoteAddr(), message);
        ResponseError error = new ResponseError(
                HttpStatus.UNAUTHORIZED.value(),
                message != null ? message : "Authentication failed"
        );

        objectMapper.writeValue(response.getWriter(), error);
    }
}