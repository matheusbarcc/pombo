package com.pruu.pombo.auth;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    public AuthService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String authenticate(Authentication authentication) {
        return jwtService.generateToken(authentication);
    }

    public User getAuthenticatedUser() throws PomboException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = null;

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            Jwt jwt = (Jwt) principal;
            String login = jwt.getClaim("sub");

            authenticatedUser = userRepository.findByEmail(login).orElseThrow(() -> new PomboException("Usuário não encontrado.", HttpStatus.BAD_REQUEST));
        }

        if(authenticatedUser == null) {
            throw new PomboException("Usuário não encontrado.", HttpStatus.BAD_REQUEST);
        }

        return authenticatedUser;
    }
}
