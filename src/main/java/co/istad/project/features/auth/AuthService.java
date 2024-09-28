package co.istad.project.features.auth;


import co.istad.project.features.auth.dto.AuthRequest;
import co.istad.project.features.auth.dto.AuthResponse;
import co.istad.project.features.auth.dto.RefreshTokenRequest;
import co.istad.project.features.user.dto.ResponseUserDto;
import co.istad.project.features.user.dto.UserRegisterDto;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);
    ResponseUserDto createUser(UserRegisterDto userRegisterDto);
    AuthResponse refreshToken(RefreshTokenRequest refreshToken);
}
