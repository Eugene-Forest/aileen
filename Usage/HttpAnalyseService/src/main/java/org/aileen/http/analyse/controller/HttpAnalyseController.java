package org.aileen.http.analyse.controller;

import org.aileen.http.analyse.service.HttpAnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/12/16
 */
@RestController
@RequestMapping("/analyse")
public class HttpAnalyseController {

    @Autowired
    private HttpAnalyseService httpAnalyseService;

    @GetMapping("/get")
    public Object getAnalyse(String... args){
        return httpAnalyseService.analyseGetRequest(args);
    }

    @PostMapping("/post")
    public Object postAnalyse(@RequestBody String body){
        return httpAnalyseService.analysePostRequest(body);
    }

}
