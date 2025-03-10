package org.aileen.mod.datasource.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
//@RefreshScope
@Slf4j
public class HttpByService {

//    @Value("${datasourceset.url}")
    private static final String DATASOURCE_SET_URL = "datasourceset.url";
}
