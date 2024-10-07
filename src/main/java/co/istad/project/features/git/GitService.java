package co.istad.project.features.git;

import co.istad.project.features.git.dto.GitRepositoryResponse;
import org.springframework.http.ResponseEntity;

public interface GitService {

    /**
     * Get list of repositories from a user
     *
     * @param username
     * @return List of repositories
     * author: Lyhou
     */
    ResponseEntity<GitRepositoryResponse> getListRepositories(String username);


}
