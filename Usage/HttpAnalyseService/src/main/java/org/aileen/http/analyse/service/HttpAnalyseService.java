package org.aileen.http.analyse.service;

import java.util.Map;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/12/16
 */
public interface HttpAnalyseService {

    Object analyseGetRequest(Map<String,String> args);

    Object analysePostRequest(String body);
}
