package co.istad.project.config;

import lombok.RequiredArgsConstructor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@RequiredArgsConstructor
public class GitConfig {

    public String gitClone(String url, String branch, String cloneDir)
    {

        String projectName = "spring-boot-" + java.util.UUID.randomUUID();

        File dir = new File(cloneDir + projectName);

        if (!dir.exists()) {
            dir.mkdir();
        }
        try{

            Git.cloneRepository()
                    .setURI(url)
                    .setDirectory(dir)
                    .setBranch(branch)
                    .call();

            return projectName;
        }catch (GitAPIException e)
        {
            throw new RuntimeException("Failed to clone repository", e);
        }


    }


}
