package co.istad.project.features.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class SourceServiceImpl implements SourceService{

    @Value("${sonar.url}")
    private String sonarUrl;

    @Value("${sonar.token}")
    private String sonarToken;


    private final RestTemplate restTemplate;

    @Override
    public HashMap getCodeIssue(String issueKey) throws Exception{
        // Correct URL to SonarQube API endpoint
        String url = sonarUrl + "/api/sources/issue_snippets?issueKey=" + issueKey;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + sonarToken); // Ensure this is correct for your token type
        HttpEntity<String> entity = new HttpEntity<>(headers);
        // Create the body for the request
//        get response from sonar API
        return new ObjectMapper().readValue(restTemplate.exchange(url, HttpMethod.GET,entity,String.class).getBody(), HashMap.class);
    }

}
