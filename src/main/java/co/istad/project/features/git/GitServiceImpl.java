package co.istad.project.features.git;

import co.istad.project.features.git.dto.GitRepositoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GitServiceImpl implements GitService {

    private final WebClient webClient;

    @Override
    public Flux<GitRepositoryResponse> getRepositoriesByUser(String username,String projectName) {

        return webClient.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .bodyToFlux(GitRepositoryResponse.class)
                .filter(repository -> repository.name().toLowerCase().contains(projectName.toLowerCase()))
                .onErrorMap(
                        throwable -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "User not found", throwable));


    }

    @Override
    public Flux<GitRepositoryResponse> getRepositories(String username, String projectName) {

        return webClient.get()
                .uri("/repos/{username}/{projectName}", username, projectName)
                .retrieve()
                .bodyToFlux(GitRepositoryResponse.class)
                .onErrorMap(
                        throwable -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Repository not found", throwable));
    }




}
