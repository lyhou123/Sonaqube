package co.istad.project.features.auth;


import co.istad.project.features.auth.dto.AuthRequest;
import co.istad.project.features.auth.dto.AuthRespone;

public interface AuthService {
    AuthRespone login(AuthRequest authRequest);
    AuthRespone signup(AuthRequest authRequest);
    AuthRespone refreshToken(String refreshToken);
}
