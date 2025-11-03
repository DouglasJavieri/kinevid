package com.kinevid.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

@Configuration
public class InjectionConfiguration {

//    @Bean
//    public AuditorAware<String> auditorAware() {
//        return () -> {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if ((authentication != null) && (authentication.getPrincipal() != null)) {
//                if (authentication.getPrincipal() instanceof Jwt) {
//                    Jwt jwt = (Jwt) authentication.getPrincipal();
//                    String fullName = jwt.getClaim("name");
//                    return Optional.of(fullName);
//                }
//            }
//
//            if (authentication == null || !authentication.isAuthenticated()) {
//                return Optional.of("ADMIN");
//            }
//
//            try {
//                return Optional.ofNullable(authentication.getName());
//            } catch (Exception e) {
//                return Optional.of("ADMIN");
//            }
//        };
//    }
}