package com.rapidrescue.ambulancewale.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class WebsiteController {
    @Value("${app.base-url}")
    private String baseUrl;

    @GetMapping(value = {"/home","/"})
     public String website(Model model) {
        model.addAttribute("baseUrl", baseUrl);
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contactus";
    }
}
