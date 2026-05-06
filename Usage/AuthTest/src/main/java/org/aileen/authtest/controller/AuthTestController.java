package org.aileen.authtest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth-test")
public class AuthTestController {

    @PostMapping("/login")
    public Object login(@RequestBody Object object){
        return null;
    }

    @GetMapping("/logout")
    public Object logout(){
        return null;
    }

    @GetMapping("/getUserInfo")
    public Object getUserInfo(){
        return null;
    }
}
