package org.aileen.lc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/12/27
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class}, scanBasePackages = {"org.aileen"})
public class LCM3v81 {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(LCM3v81.class, args);
    }
}
