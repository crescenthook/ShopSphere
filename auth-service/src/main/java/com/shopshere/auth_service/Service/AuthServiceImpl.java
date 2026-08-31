package com.shopshere.auth_service.Service;

import com.shopshere.auth_service.DTO.LoginRequestDto;
import com.shopshere.auth_service.DTO.LoginResponseDto;
import com.shopshere.auth_service.DTO.RegisterRequestDto;
import com.shopshere.auth_service.Entity.User;
import com.shopshere.auth_service.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    @Override
    public void register(RegisterRequestDto request) {

        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new RuntimeException("User Already Exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password.");
        }

        String token = jwtUtil.generateToken(request.getUsername());

        return new LoginResponseDto(token);
    }
}
