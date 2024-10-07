package co.istad.project.features.git.dto;

import lombok.Builder;

@Builder
public record GitRepositoryRequest(

        String username

) {


}
