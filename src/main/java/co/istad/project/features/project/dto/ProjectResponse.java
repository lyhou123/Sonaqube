package co.istad.project.features.project.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ProjectResponse(

        String uuid,
        String projectName,
        LocalDateTime createdDate,
        Boolean isDeleted,
        Boolean isUsed

) {
}
