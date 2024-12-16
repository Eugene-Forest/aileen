package org.aileen.datasourceset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/19
 */
@SpringBootApplication(scanBasePackages = {"org.aileen"})
public class DataSourceSetApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataSourceSetApplication.class, args);
    }
}
