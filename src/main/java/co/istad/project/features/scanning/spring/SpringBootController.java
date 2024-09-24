package co.istad.project.features.scanning.spring;

import co.istad.project.respone.BaseRestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/scans")
public class SpringBootController {

    private final SpringBootService springBootService;

    @GetMapping("/spring-boot")
    public BaseRestResponse<Object> springBootScanner(@RequestParam String gitUrl,
                                                      @RequestParam String gitBranch,
                                                      @RequestParam String projectName) throws Exception {
        return BaseRestResponse
                .builder()
                .data(springBootService.springBootScanning(gitUrl, gitBranch, projectName))
                .message("Spring boot project has been scanned successfully.")
                .build();
    }

}
