package co.istad.project.features.scanning.next;

import co.istad.project.config.AppConfig;
import co.istad.project.config.GitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    public String nextScanning(String gitUrl, String branch, String projectName) throws Exception {

        String currentProjectDir = appConfig.getProjectAbsolutePath();
        String cloneDirect = currentProjectDir + clone_dir;

        // Clone the Next.js project to the specified directory
        String fileName = gitConfig.gitClone(gitUrl, branch, cloneDirect);

        String binariesPath = "out/.next"; // Assuming you're using the default build output directory for Next.js

        List<String> command = new ArrayList<>();
        command.add("docker");
        command.add("run");
        command.add("--rm");
        command.add("-v");
        command.add("/usr/src");
        command.add("sonarsource/sonar-scanner-cli");
        command.add("-Dsonar.projectKey=" + projectName);
        command.add("-Dsonar.projectName=" + projectName);
        command.add("-Dsonar.host.url=" + sonarHostUrl);
        command.add("-Dsonar.token=" + sonarLoginToken);
        command.add("-Dsonar.sources=."); // The source code directory for Next.js
        command.add("-Dsonar.java.binaries=" + binariesPath); // The output directory for binaries in Next.js
        command.add("-X"); // Debug mode

        // Log the command being executed
        System.out.println("Executing command: " + String.join(" ", command));

        // Execute the SonarQube scan
        try {
            scanProject(command);
        } catch (Exception e) {
            System.err.println("Error during SonarQube scan: " + e.getMessage());
            e.printStackTrace(); // Log the stack trace for further analysis
            throw new RuntimeException("SonarQube scan failed", e); // Rethrow as a runtime exception
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
