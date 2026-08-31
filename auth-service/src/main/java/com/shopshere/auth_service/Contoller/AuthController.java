package com.shopshere.auth_service.Contoller;

import com.shopshere.auth_service.DTO.LoginRequestDto;
import com.shopshere.auth_service.DTO.LoginResponseDto;
import com.shopshere.auth_service.DTO.RegisterRequestDto;
import com.shopshere.auth_service.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDto request) {

        authService.register(request);

        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request){

        LoginResponseDto response = authService.login(request);

        return ResponseEntity.ok(response);
    }
}
