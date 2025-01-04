package org.aileen.http.analyse.service.impl;

import com.alibaba.fastjson.JSON;
import org.aileen.http.analyse.service.HttpAnalyseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/12/16
 */
@Service
public class HttpAnalyseServiceImpl implements HttpAnalyseService {

    private static final Logger log = LoggerFactory.getLogger(HttpAnalyseServiceImpl.class);

    @Override
    public Object analyseGetRequest(Map<String,String> args) {
        log.info("analyseGetRequest");
        args.forEach((k,v)->log.info("param: " + k + "=" + v));
        return args.size();
    }

    @Override
    public Object analysePostRequest(Object body) {
        log.info("analysePostRequest");
        log.info("body: " + JSON.toJSONString(body));
        return "i get it";
    }
}
