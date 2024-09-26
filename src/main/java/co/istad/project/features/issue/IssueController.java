package co.istad.project.features.issue;

import co.istad.project.respone.BaseRestResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;


    @Operation(
            summary = "Search for all issues in project",
            description = "This is used for get all issues in a project after scanning."
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseRestResponse<Object> getAllIssuesOnProject(@RequestParam String projectName) throws Exception {

        return BaseRestResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .data(issueService.getIssueByProjectName(projectName))
                .message("Get issue by project name.")
                .build();

    }



    @Operation(
            summary = "Search specific issue by issueKey",
            description = "This is used for get specific issue detail"
    )
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)

    public BaseRestResponse<Object> getIssueDetail(@RequestParam String issueKey, @RequestParam String ruleKey) throws Exception {

        return BaseRestResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .data(issueService.getIssueByIssueKey(issueKey, ruleKey))
                .message("Get issue by project name.")
                .build();

    }


}
