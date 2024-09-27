package co.istad.project.features.project;

import co.istad.project.domain.Project;
import co.istad.project.features.project.dto.ProjectRequest;
import co.istad.project.features.project.dto.ProjectResponse;
import co.istad.project.respone.SonaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    @Value("${sonar.url}")
    private String sonarUrl;

    @Value("${sonar.token}")
    private String sonarToken;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final SonaResponse sonaResponse;

    @Override
    public ProjectResponse createProject(ProjectRequest projectRequest) throws Exception {

        String url = sonarUrl + "/api/projects/create";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + sonarToken);

        String projectName = projectRequest.projectName().replace(" ", "-");

        String body = "project=" + projectName +
                "&name=" + projectName +
                "&visibility=" + "public";

        Boolean isProjectAlreadyExisted = projectRepository.existsByProjectName(projectRequest.projectName());

        if(isProjectAlreadyExisted){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ProjectName already existed");
        }


        Project project = projectRepository.save(
                Project.builder()
                        .projectName(projectName)
                        .uuid(UUID.randomUUID().toString())
                        .createdDate(LocalDateTime.now())
                        .isDeleted(false)
                        .isUsed(false)
                        .build()
        );

        sonaResponse.responseFromSonarAPI(url, body, httpHeaders, HttpMethod.POST);

        return projectMapper.mapToProjectResponse(project);

    }

    @Override
    public List<ProjectResponse> getAllProject() {

        if(projectRepository.findAll().isEmpty()){

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project is empty");

        }

        return projectRepository.findAll().stream()
                .map(projectMapper::mapToProjectResponse)
                .toList();

    }

    @Override
    public ProjectResponse getProjectByProjectName(String projectName) {

        var findProject = projectRepository.findByProjectName(projectName)

                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "ProjectName not found"));

        return projectMapper.mapToProjectResponse(findProject);
    }

    @Override
    public String deleteProjectByProjectName(String projectName) {

        String url = sonarUrl + "/api/projects/delete?project=" + projectName;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + sonarToken);

        var entity = new HttpEntity<>(httpHeaders);

        var response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

//        check if project name doesn't exist
        if(response.getStatusCode() == HttpStatus.NOT_FOUND){

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ProjectName not found");

        }


        if(response.getStatusCode() == HttpStatus.NO_CONTENT){

            projectRepository.deleteProjectByProjectName(projectName);

            return response.getBody();

        } else {

            throw new RuntimeException("Failed to delete project: " + response.getStatusCode());

        }


    }
}

