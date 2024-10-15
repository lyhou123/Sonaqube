package co.istad.project.features.scanning.next;

import co.istad.project.respone.BaseRestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/scanning/next")
@RequiredArgsConstructor

public class NextController {

    private final NextService nextService;

    @GetMapping
    public BaseRestResponse<Object> nextScanning(String gitUrl, String branch, String projectName) throws Exception {

        return BaseRestResponse.builder()
                .data(nextService.nextScanning(gitUrl, branch, projectName))
                .message("Next project has been scanned successfully.")
                .status(200)
                .build();
    }

@GetMapping("new")
    public BaseRestResponse<Object> newScanning(String gitUrl, String branch, String projectName) throws Exception {

        return BaseRestResponse.builder()
                .data(nextService.newNextScanner(gitUrl, branch, projectName))
                .message("New project has been scanned successfully.")
                .status(200)
                .build();
    }




}
