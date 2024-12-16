package org.aileen.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/29
 */
@ComponentScan(basePackages = {"org.aileen"})
@SpringBootApplication
public class ClientDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientDemoApplication.class, args);
    }
}
