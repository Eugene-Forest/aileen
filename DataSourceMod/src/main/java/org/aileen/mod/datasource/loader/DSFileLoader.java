package org.aileen.mod.datasource.loader;

import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.datasource.interfaces.DataSourceSetLoader;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class DSFileLoader implements DataSourceSetLoader {

    private String filePath;

    public DSFileLoader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getDataSourceSet() {
        try {
            File file = new File(filePath);
            byte[] bytes = FileCopyUtils.copyToByteArray(file);
            String dataSourceSet = new String(bytes, StandardCharsets.UTF_8);
            log.info("dataSourceSet: {}", dataSourceSet);
            return dataSourceSet;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
