package co.istad.project.features.git;

import co.istad.project.features.git.dto.GitRepositoryResponse;
import co.istad.project.features.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class GitServiceImpl implements GitService {

    private final RestTemplate restTemplate;

    private final UserRepository userRepository;

    private final GitMapper gitMapper;

    @Override
    public ResponseEntity<GitRepositoryResponse> getListRepositories(String username) {

//        var user = userRepository.findUsername(username).orElseThrow(
//
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Username not found")
//
//        );


        String url = "https://api.github.com/users/" + username + "/repos";

        var response = restTemplate.getForEntity(url, GitRepositoryResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {

              return response;

        } else if(response.getStatusCode().is4xxClientError()) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not found");

        } else {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error on Github API");
        }

    }
}
