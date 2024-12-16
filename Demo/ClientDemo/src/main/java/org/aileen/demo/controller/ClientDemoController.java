package org.aileen.demo.controller;

import org.aileen.demo.entity.SimpleHttpProxyDto;
import org.aileen.demo.service.ClientDemoService;
import org.aileen.mod.auth.entity.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
