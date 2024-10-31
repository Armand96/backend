package com.gaboot.backend.auth;

import com.gaboot.backend.auth.dto.LoginDto;
import com.gaboot.backend.common.exception.BadRequestException;
import com.gaboot.backend.master.user.UserRepo;
import com.gaboot.backend.master.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepo userRepository;
    private final AuthenticationManager authenticationManager;

    public User login(LoginDto input) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getUsername(),
                            input.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadRequestException("Invalid username or password");
        }

        return userRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("No user found"));
    }
}