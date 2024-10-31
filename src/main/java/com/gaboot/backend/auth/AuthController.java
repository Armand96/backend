package com.gaboot.backend.auth;

import com.gaboot.backend.auth.dto.LoginDto;
import com.gaboot.backend.auth.dto.LoginResponseDto;
import com.gaboot.backend.master.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
@AllArgsConstructor
// @Validated
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginDto loginDto) {
        System.out.println("LOGIN START");
        User authenticatedUser = authenticationService.login(loginDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        System.out.println("LOGIN: END");

        LoginResponseDto loginResponse = new LoginResponseDto();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
