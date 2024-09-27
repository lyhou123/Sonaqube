package co.istad.project.features.project;

import co.istad.project.features.project.dto.ProjectRequest;
import co.istad.project.respone.BaseRestResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor

public class ProjectController {

    private final ProjectService projectService;

    @Operation(
            summary = "Create a project"
    )
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseRestResponse<Object> createProject(@RequestBody ProjectRequest projectRequest) throws Exception {
        return BaseRestResponse
                .builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .data(projectService.createProject(projectRequest))
                .message("Project has been created successfully.")
                .build();
    }



    @Operation(
            summary = "Get all projects",
            description = "This endpoint is used for listing all projects"
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseRestResponse<Object> getAllProject()
    {
       return BaseRestResponse
               .builder()
               .timestamp(LocalDateTime.now())
               .status(HttpStatus.OK.value())
               .data(projectService.getAllProject())
               .message("List all projects")
               .build();
    }


    @Operation(
            summary = "Get project by project name",
            description = "This endpoint is used for getting project by project name"
    )
    @GetMapping("/{projectName}")
    public BaseRestResponse<Object> getProjectByName(@PathVariable String projectName) {
        return BaseRestResponse
                .builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .data(projectService.getProjectByProjectName(projectName))
                .message("Project has been retrieved successfully.")
                .build();
    }


    @Operation(
            summary = "Delete project by project name",
            description = "This endpoint is used for deleting project by project name"
    )
    @DeleteMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public BaseRestResponse<Object> deleteProjectByName(@RequestParam String projectName) {
        return BaseRestResponse
                .builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NO_CONTENT.value())
                .data(projectService.deleteProjectByProjectName(projectName))
                .message("Project " + projectName + " has been deleted successfully.")
                .build();
    }


}
