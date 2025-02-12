package org.aileen.lc.config;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@org.springframework.context.annotation.Configuration
public class FreeMarkerConfig {

    private final ResourceLoader resourceLoader;

    @Autowired
    public FreeMarkerConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public Configuration getConfiguration() throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);

        Resource resource = resourceLoader.getResource("classpath:templates");
        File templateDir = resource.getFile();

        cfg.setDirectoryForTemplateLoading(templateDir);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        return cfg;
    }
}
