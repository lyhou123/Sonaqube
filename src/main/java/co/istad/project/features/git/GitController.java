package co.istad.project.features.git;

import co.istad.project.respone.BaseRestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController("/api/v1/git")
@RequiredArgsConstructor
public class GitController {

    private final GitService gitService;

    @GetMapping("/{username}")
    public BaseRestResponse<Object> getListRepositories(@PathVariable String username) {

        return BaseRestResponse.builder()
                .data(gitService.getListRepositories(username).getBody())
                .status(200)
                .message("Success")
                .timestamp(LocalDateTime.now())
                .build();

    }


}
