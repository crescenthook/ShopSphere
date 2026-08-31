package com.shopshere.auth_service.Service;

import com.shopshere.auth_service.DTO.LoginRequestDto;
import com.shopshere.auth_service.DTO.LoginResponseDto;
import com.shopshere.auth_service.DTO.RegisterRequestDto;

public interface AuthService {

    void register(RegisterRequestDto request);

    LoginResponseDto login(LoginRequestDto request);
}
