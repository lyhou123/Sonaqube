package co.istad.project.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    public String getProjectAbsolutePath() {
        return System.getProperty("user.dir");
    }

}
