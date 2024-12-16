package org.aileen.http.analyse.service;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/12/16
 */
public interface HttpAnalyseService {

    Object analyseGetRequest(String args[]);

    Object analysePostRequest(String body);
}
