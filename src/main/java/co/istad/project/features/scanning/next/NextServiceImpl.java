package co.istad.project.features.scanning.next;

import co.istad.project.config.AppConfig;
import co.istad.project.config.GitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NextServiceImpl implements NextService{

    @Value("${git.clone_directory}")
    private String clone_dir;

    @Value("${sonar.url}")
    private String sonarHostUrl;

    @Value("${sonar.token}")
    private String sonarLoginToken;

    private final AppConfig appConfig;

    private final GitConfig gitConfig;
    @Override
    public String nextScanning(String gitUrl, String branch, String projectName)  {

        if (gitUrl == null || gitUrl.isEmpty() || projectName == null || projectName.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Git URL and project name are required");
        }

        String currentProjectDir = appConfig.getProjectAbsolutePath();
        String cloneDirect = currentProjectDir + clone_dir;

        String fileName = gitConfig.gitClone(gitUrl, branch, cloneDirect);

        List<String> command = new ArrayList<>();
        command.add("docker");
        command.add("run");
        command.add("--rm");
        command.add("-v");
        command.add(cloneDirect + ":/usr/src");  // Mount your cloned directory
        command.add("-w");
        command.add("/usr/src");  // Set working directory in Docker
        command.add("sonarsource/sonar-scanner-cli");
        command.add("-Dsonar.projectKey=" + projectName);
        command.add("-Dsonar.projectName=" + projectName);
        command.add("-Dsonar.host.url=" + sonarHostUrl);
        command.add("-Dsonar.token=" + sonarLoginToken);
        command.add("-Dsonar.sources=.");
        command.add("-X");

        // Execute the SonarQube scan
        try {
            scanProject(command);

        } catch (Exception e) {

            System.err.println("Error during SonarQube scan: " + e.getMessage());

            throw new RuntimeException("SonarQube scan failed", e); // Rethrow as a runtime exception
        }

        return fileName;

    }

    @Override
    public String newNextScanner(String gitUrl, String branch, String projectName) throws Exception {


        String currentProjectDir = appConfig.getProjectAbsolutePath();

        String cloneDirect = currentProjectDir + clone_dir;

        System.out.println("this is clone direct"+cloneDirect);


        String fileName = gitConfig.gitClone(gitUrl, branch, cloneDirect);

        System.out.println("this is file name"+fileName);

        // Prepare and start the SonarScanner process
        ProcessBuilder processBuilder = new ProcessBuilder()
                .command("sonar-scanner",
                        "-Dsonar.projectKey=" + projectName,
                        "-Dsonar.projectName=" + projectName,
                        "-Dsonar.host.url=" + sonarHostUrl,
                        "-Dsonar.login=" + sonarLoginToken,
                        "-Dsonar.sources=" + cloneDirect) // Specify source directory
                .redirectErrorStream(true);

        Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("OUTPUT: " + line);
                }

                String lineError;
                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                    while ((lineError = errorReader.readLine()) != null) {
                        System.out.println("ERROR: " + lineError);
                    }
                }

                int exitCode = process.waitFor();
                System.out.println("SonarQube scan completed with exit code " + exitCode);

                if (exitCode != 0) {
                    throw new RuntimeException("SonarQube scan failed with exit code " + exitCode);
                }


            }catch (Exception e) {
                System.err.println("Error during SonarQube scan: " + e.getMessage());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error during SonarQube scan:"); // Rethrow as a runtime exception
            }

        return fileName;
    }

    public void scanProject(List<String> command) throws Exception {

        // Execute the command
        ProcessBuilder processBuilder = new ProcessBuilder(command);

        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        // Log the output from the scanner
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("OUTPUT: " + line);
            }

            String lineError;
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                while ((lineError = errorReader.readLine()) != null) {
                    System.out.println("ERROR: " + lineError);
                }
            }

            int exitCode = process.waitFor();
            System.out.println("SonarQube scan completed with exit code " + exitCode);

            if (exitCode != 0) {
                throw new RuntimeException("SonarQube scan failed with exit code " + exitCode);
            }
        }
    }


}
