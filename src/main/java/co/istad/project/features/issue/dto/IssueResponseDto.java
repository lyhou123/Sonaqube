package co.istad.project.features.issue.dto;

import lombok.Builder;

@Builder
public record IssueResponseDto(
        Object rule,
        Object snippetError,
        Object issue
) {
}
