package com.gaboot.backend.auth;

import com.gaboot.backend.auth.dto.LoginDto;
import com.gaboot.backend.auth.dto.LoginResponseDto;
import com.gaboot.backend.auth.dto.RefreshTokenReq;
import com.gaboot.backend.common.dto.ResponseDto;
import com.gaboot.backend.master.user.entity.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/auth")
@AllArgsConstructor
@Validated
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final JwtService jwtService;
    private final AuthService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@Valid @RequestBody LoginDto loginDto) {
        System.out.println("LOGIN START");
        User authenticatedUser = authenticationService.login(loginDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        String refreshToken = jwtService.generateRefreshToken(authenticatedUser);

        LoginResponseDto loginResponse = new LoginResponseDto();
        loginResponse.setToken(jwtToken);
        loginResponse.setRefreshToken(refreshToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        System.out.println("LOGIN: END");
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(@Valid @RequestBody RefreshTokenReq req) {
        String refreshToken = req.getRefreshToken();
        if (jwtService.isTokenValid(refreshToken)) {
            String username = jwtService.extractUsername(refreshToken);
            User currentUser = new User();
            currentUser.setUsername(username);
            String newAccessToken = jwtService.generateToken(currentUser);
            return ResponseEntity.ok(new LoginResponseDto(newAccessToken, refreshToken, jwtService.getExpirationTime()));
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized to access this");
        // return ResponseEntity.status(401).body("Invalid refresh token");
    }

    @GetMapping("/currentUser")
    public ResponseEntity<ResponseDto<User>> currentUser() {
        logger.error("TEST LOGGER");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        User user = (User) authentication.getPrincipal();

        ResponseDto<User> resp = new ResponseDto<>();
        resp.setSuccess(true);
        resp.setMessage("OK");
        resp.setDatum(user);

        return ResponseEntity.ok(resp);
    }
}
