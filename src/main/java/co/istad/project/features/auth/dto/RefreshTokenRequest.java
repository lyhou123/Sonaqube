package co.istad.project.features.auth.dto;

import lombok.Builder;

@Builder
public record RefreshTokenRequest(
        String refreshToken
) {
}
