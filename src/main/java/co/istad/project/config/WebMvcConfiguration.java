package co.istad.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Value("${file_storage.image_location}")
    private String fileStorageLocation;

    @Value("${file_storage.client_path}")
    private String clientLocation;

    @Value("${git.clone_directory}")
    private String clone_dir;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String resourcePath = "file:" + fileStorageLocation;
        System.out.println("Serving static files from: " + resourcePath);

        registry.addResourceHandler(clientLocation)
                .addResourceLocations("file:" + fileStorageLocation);

        registry.addResourceHandler(clone_dir);
    }


}
