package org.aileen.http.analyse.controller;

import org.aileen.http.analyse.service.HttpAnalyseService;
import org.aileen.mod.auth.anno.EncryptRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/12/16
 */
@RestController
@EncryptRequest
@RequestMapping("/analyse")
public class HttpAnalyseController {

    private static final Logger log = LoggerFactory.getLogger(HttpAnalyseController.class);
    @Autowired
    private HttpAnalyseService httpAnalyseService;

    @GetMapping("/get")
    public Object getAnalyse(@RequestParam Map<String,String> args){
        return httpAnalyseService.analyseGetRequest(args);
    }

    @PostMapping("/post")
    public Object postAnalyse(@RequestBody Object body){
        log.info("postAnalyse");
        return httpAnalyseService.analysePostRequest(body);
    }

}
