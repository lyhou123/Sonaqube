package co.istad.project.features.git.dto;

import lombok.Builder;

@Builder
public record GitRepositoryResponse(

            String name,
            String description,
            String url,
            String language,
            String stars,
            String forks,
            String lastUpdate

) {
}
