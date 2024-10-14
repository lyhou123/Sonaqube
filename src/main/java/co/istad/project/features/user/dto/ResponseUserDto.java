package co.istad.project.features.user.dto;

import co.istad.project.features.role.dto.RoleResponseDto;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record ResponseUserDto(
        String uuid,
        String name,
        String email,
        String profile,
        String bio,
        LocalDateTime registeredDate,
        LocalDateTime updatedDate,
        Boolean isVerified,
        Boolean isDeleted,
        Set<RoleResponseDto> roles
) {
}
