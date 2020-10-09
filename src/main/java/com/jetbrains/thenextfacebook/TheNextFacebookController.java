package com.jetbrains.thenextfacebook;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TheNextFacebookController {

    @GetMapping
    public String index() {
        return "Hello World";
    }
}
