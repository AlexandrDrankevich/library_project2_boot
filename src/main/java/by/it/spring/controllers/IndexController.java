package by.it.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping()
    public String index() {
        return "index";
    }

    @GetMapping("/auth_index")
    public String authIndex() {
        return "auth_index";
    }
}