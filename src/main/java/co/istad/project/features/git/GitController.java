package co.istad.project.features.git;

import co.istad.project.features.git.dto.GitRepositoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/api/v1/git")
@RequiredArgsConstructor
public class GitController {

    private final GitService gitService;

    @GetMapping("/repos/{username}")
    public Flux<GitRepositoryResponse> getAllGitRepository(@PathVariable String username)
    {
        return gitService.getRepositoriesByUser(username);

    }

    @GetMapping("/repos/{username}/{projectName}")
    public Flux<GitRepositoryResponse> getGitRepository(@PathVariable String username, @PathVariable String projectName)
    {
        return gitService.getRepositories(username, projectName);

    }

}
