package co.istad.project.respone;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class SonaResponse {

    private final RestTemplate restTemplate = new RestTemplate();
    public HashMap responseFromSonarAPI(String url, String body, HttpHeaders headers, HttpMethod httpMethod) throws Exception{
        var entity = new HttpEntity<>(body, headers);
        // Correct URL variable usage
        var response = restTemplate.exchange(url, httpMethod, entity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.readValue(response.getBody(), HashMap.class);
        }catch (Exception exception){
            throw new Exception(exception.getMessage());
        }
    }
}

