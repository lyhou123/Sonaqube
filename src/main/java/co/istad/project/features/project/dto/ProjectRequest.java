package co.istad.project.features.project.dto;

import lombok.Builder;

@Builder
public record ProjectRequest(
        String projectName
) {
}
