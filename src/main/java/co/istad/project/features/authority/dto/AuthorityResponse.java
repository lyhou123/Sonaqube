package co.istad.project.features.authority.dto;


import lombok.Builder;

@Builder
public record AuthorityResponse(

        String uuid,
        String authorityName,
        String description
) {
}
