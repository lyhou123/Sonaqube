package co.istad.project.features.authority.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AuthorityRequestToUser(
        @NotNull(message = "Authority name is required")
        String authorityName
) {
}
