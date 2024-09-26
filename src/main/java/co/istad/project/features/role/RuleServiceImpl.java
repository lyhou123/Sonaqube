package co.istad.project.features.role;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class RuleServiceImpl implements RuleService{

    @Value("${sonar.url}")
    private String sonarUrl;

    @Value("${sonar.token}")
    private String sonarToken;

    private final RestTemplate restTemplate;

    @Override
    public Object getRuleDetails(String roleKey) throws Exception {

        String url = sonarUrl + "/api/rules/show?key=" + roleKey;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + sonarToken);
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, entity, String.class).getBody();


    }
}
