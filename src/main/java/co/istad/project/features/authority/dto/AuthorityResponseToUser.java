package co.istad.project.features.authority.dto;

import lombok.Builder;

@Builder
public record AuthorityResponseToUser(
        String authorityName
) {
}
