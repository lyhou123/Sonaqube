package co.istad.project.features.git;

import co.istad.project.features.git.dto.GitRepositoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/api/v1/git")
@RequiredArgsConstructor
public class GitController {

    private final GitService gitService;

    @GetMapping("/repos/{username}")
    public Flux<GitRepositoryResponse> getAllGitRepository(@PathVariable String username,
                                                           @RequestParam(required = false) String projectName)
    {
//        check if projectName is null or empty then return all repositories
        if(projectName == null || projectName.isEmpty())
        {

            return gitService.getRepositoriesByUser(username,"");

        }

//        else return repositories by project name
        return gitService.getRepositoriesByUser(username,projectName);

    }

    @GetMapping("/repos/{username}/{projectName}")
    public Flux<GitRepositoryResponse> getGitRepository(@PathVariable String username, @PathVariable String projectName)
    {
        return gitService.getRepositories(username, projectName);

    }



}
