package co.istad.project.features.git;

import co.istad.project.features.git.dto.GitRepositoryResponse;
import reactor.core.publisher.Flux;

public interface GitService {


    /**
     * Get all repositories from a user
     *
     * @return Flux<GitRepositoryResponse>
     *
     * author: lyhou
     */
    Flux<GitRepositoryResponse> getRepositoriesByUser(String username,String projectName);

    /**
     * Get a repository from a user
     *
     * @return Flux<GitRepositoryResponse>
     */

    Flux<GitRepositoryResponse> getRepositories(String username, String projectName);




}
