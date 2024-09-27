package co.istad.project.features.user.dto;

import lombok.Builder;

@Builder
public record UpdateUserDto(
        String name,
        String profile,
        String bio
) {
}
