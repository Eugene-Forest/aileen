package org.aileen.http.analyse.service.impl;

import org.aileen.http.analyse.service.HttpAnalyseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/12/16
 */
@Service
public class HttpAnalyseServiceImpl implements HttpAnalyseService {

    private static final Logger log = LoggerFactory.getLogger(HttpAnalyseServiceImpl.class);

    @Override
    public Object analyseGetRequest(String[] args) {
        log.info("analyseGetRequest");
        return args;
    }

    @Override
    public Object analysePostRequest(String body) {
        log.info("analysePostRequest");
        return body;
    }
}
