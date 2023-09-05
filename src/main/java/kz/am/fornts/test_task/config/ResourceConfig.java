package kz.am.fornts.test_task.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        File filesDir = new File(String.format("%s/files", System.getProperty("user.dir")));
        if (!filesDir.exists()) filesDir.mkdirs();
        registry.addResourceHandler("/files/**").addResourceLocations("file:files/");
    }
}
