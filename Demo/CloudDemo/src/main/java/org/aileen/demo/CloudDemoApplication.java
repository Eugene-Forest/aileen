package org.aileen.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/12/16
 */
@EnableDiscoveryClient
@SpringBootApplication
public class CloudDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudDemoApplication.class, args);
    }
}
