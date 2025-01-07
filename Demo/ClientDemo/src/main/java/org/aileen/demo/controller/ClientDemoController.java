package org.aileen.demo.controller;

import org.aileen.demo.service.ClientDemoService;
import org.aileen.mod.kit.dto.WebResult;
import org.aileen.mod.httpclient.dto.SimpleHttpProxyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/29
 */
@RequestMapping("/client")
@RestController
public class ClientDemoController {

    @Autowired
    private ClientDemoService clientDemoService;

    @PostMapping("/post")
    public WebResult proxyPostSignHttp(@RequestBody SimpleHttpProxyDto dto) {
        return clientDemoService.proxyHttp(dto);
    }

    @PostMapping("/login")
    public WebResult proxyLogin() {
        return clientDemoService.proxyLogin();
    }

    @GetMapping("/analyseGet")
    public WebResult proxyAnalyseGet() {
        return clientDemoService.proxyAnalyseGet();
    }

    @PostMapping("/analysePost")
    public WebResult proxyAnalysePost() {
        return clientDemoService.proxyAnalysePost();
    }

    @GetMapping("/analysePostJson")
    public WebResult proxyAnalysePostJson(@RequestParam Integer id) {
        return clientDemoService.proxyAnalysePostJson(id);
    }
}
