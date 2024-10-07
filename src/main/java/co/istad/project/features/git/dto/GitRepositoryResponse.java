package co.istad.project.features.git.dto;

import lombok.Builder;

@Builder
public record GitRepositoryResponse(

            String id,
            String name,
            String description,
            String url,
            String language,
            String size,
            String visibility,
            String watchers,
            String default_branch,
            String created_at,
            String updated_at,
            String pushed_at,
            Owner owner

) {
}
