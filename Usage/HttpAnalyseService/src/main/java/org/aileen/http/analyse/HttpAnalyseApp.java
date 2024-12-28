package org.aileen.http.analyse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/12/16
 */
@ComponentScan(basePackages = {"org.aileen"})
@SpringBootApplication
public class HttpAnalyseApp {
    public static void main(String[] args) {
        SpringApplication.run(HttpAnalyseApp.class, args);
    }
}
