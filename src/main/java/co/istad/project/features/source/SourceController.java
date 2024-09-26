package co.istad.project.features.source;


import co.istad.project.respone.BaseRestResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/sources")
@RequiredArgsConstructor

public class SourceController {

    private final SourceService sourceService;


    @Operation(
            summary = "Get code snippets issues",
            description = "This endpoint used issueKey to get issues about codes, the issueKey get from searching all issues in a project"
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)

    public BaseRestResponse<Object> getCodeSnippetIssue(@RequestParam String issueKey) throws Exception{

        return BaseRestResponse
                .builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .data(sourceService.getCodeIssue(issueKey))
                .message("Get code issues in file.")
                .build();
    }

}
