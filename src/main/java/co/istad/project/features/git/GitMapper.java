package co.istad.project.features.git;

import co.istad.project.features.git.dto.GitRepositoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GitMapper {

    GitRepositoryResponse toGitRepositoryResponse(GitRepositoryResponse gitRepositoryRequest);


}
