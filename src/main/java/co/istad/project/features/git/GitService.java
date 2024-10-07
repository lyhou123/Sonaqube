package co.istad.project.features.git;

import co.istad.project.features.git.dto.GitRepositoryResponse;
import reactor.core.publisher.Flux;

public interface GitService {


    /**
     * Get all repositories from a user
     * @param username
     * @return Flux<GitRepositoryResponse>
     *
     * author: lyhou
     */
    Flux<GitRepositoryResponse> getRepositoriesByUser(String username);

    Flux<GitRepositoryResponse> getRepositories(String username, String projectName);




}
