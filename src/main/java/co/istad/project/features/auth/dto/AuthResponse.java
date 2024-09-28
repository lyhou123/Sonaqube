package co.istad.project.features.auth.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
        String accessToken,
        String refreshToken,
        String email,
        String userId
) {
}
