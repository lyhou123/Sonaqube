package co.istad.project.features.auth.dto;

import lombok.Builder;

@Builder
public record AuthRespone(
        String accessToken,
        String refreshToken,
        String email,
        String userId
) {
}
