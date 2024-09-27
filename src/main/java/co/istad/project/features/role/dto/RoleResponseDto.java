package co.istad.project.features.role.dto;

import lombok.Builder;

@Builder
public record RoleResponseDto(
        String uuid,
        String roleName
) {
}
