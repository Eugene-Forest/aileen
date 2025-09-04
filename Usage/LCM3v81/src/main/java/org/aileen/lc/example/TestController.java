package org.aileen.lc.example;

import org.aileen.mod.auth.anno.EncryptRequest;
import org.aileen.mod.auth.enums.RequestEncryptType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
@EncryptRequest(encryptType = RequestEncryptType.RSA)
public class TestController {

    @GetMapping("/get")
    public String get() {
        return "get";
    }

    @PostMapping("/post")
    public String post() {
        return "post";
    }
}
