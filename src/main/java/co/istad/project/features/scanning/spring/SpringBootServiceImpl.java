package co.istad.project.features.scanning.spring;

import co.istad.project.config.AppConfig;
import co.istad.project.config.DetectSpringBuildTool;
import co.istad.project.config.GitConfig;
import co.istad.project.utils.SonarCustomizeScan;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SpringBootServiceImpl implements SpringBootService{

    @Value("${sonar.token}")
    private String sonarUserToken;

    @Value("${sonar.url}")
    private String sonarUrl;

    @Value("${git.clone_directory}")
    private String clone_dir;

    private final GitConfig gitConfig;
    private final AppConfig appConfig;
    private final DetectSpringBuildTool detectSpringBuildTool;
    private final SonarCustomizeScan sonarCustomizeScan;
    @Override
    public String springBootScanning(String gitUrl, String branch, String projectName) throws Exception {


        String currentProjectDir = appConfig.getProjectAbsolutePath();

        String clone_direct = currentProjectDir + clone_dir;

        String fileName = gitConfig.gitClone(gitUrl, branch, clone_direct);

        if(checkForBuildFiles(clone_direct + fileName)){

            System.out.println("Build files not found immediately. Waiting for 50 seconds ...");
            TimeUnit.SECONDS.sleep(50);

            if(checkForBuildFiles(clone_direct + fileName)){
                throw new Exception("No build.gradle or pom.xml found in the cloned repository after waiting.");
            }
        }

        String buildTool = detectSpringBuildTool.detect(clone_direct + fileName);

        System.out.println("this is build tool"+buildTool);

        if(buildTool.equalsIgnoreCase("Maven")) {

            sonarCustomizeScan.scanForMaven(clone_direct, fileName, projectName);

        } else if(buildTool.equalsIgnoreCase("Gradle")) {

            sonarCustomizeScan.scanForGradle(clone_direct, fileName, projectName);

        }



        return fileName;
    }

    @Override
    public boolean checkForBuildFiles(String projectPath) {

        return !Files.exists(Paths.get(projectPath, "build.gradle")) &&
                !Files.exists(Paths.get(projectPath, "pom.xml"));

    }
}
