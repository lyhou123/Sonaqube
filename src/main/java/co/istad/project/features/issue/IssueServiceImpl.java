package co.istad.project.features.issue;

import co.istad.project.features.issue.dto.IssueResponseDto;
import co.istad.project.features.project.ProjectRepository;
import co.istad.project.features.role.RuleService;
import co.istad.project.features.source.SourceService;
import co.istad.project.respone.SonaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService{

    @Value("${sonar.url}")
    private String sonarUrl;
    @Value("${sonar.token}")
    private String sonarUserToken;

    private final ProjectRepository projectRepository;

    private final SonaResponse sonaResponse;

    private final SourceService sourceService;

    private final RuleService ruleService;

    @Override
    public Object getIssueByProjectName(String projectName) throws Exception {

        if(projectRepository.findByProjectName(projectName).isPresent()) {
            // Correct URL to SonarQube API endpoint
            String url = sonarUrl + "/api/issues/search?components=" + projectName;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("Authorization", "Bearer " + sonarUserToken);

            return sonaResponse.responseFromSonarAPI(url, null, headers, HttpMethod.GET);
        }

        return "Project not found";
    }

    @Override
    public Object getIssueByIssueKey(String issueKey, String ruleKey) throws Exception {

        String url = sonarUrl + "/api/issues/search?issues=" + issueKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + sonarUserToken);

        return IssueResponseDto.builder()
                .rule(ruleService.getRuleDetails(ruleKey))
                .snippetError(sourceService.getCodeIssue(issueKey))
                .issue(sonaResponse.responseFromSonarAPI(url, null, headers, HttpMethod.GET))
                .build();


    }

}
