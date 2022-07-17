package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AdviceController {

    private final Environment env;

    @Autowired
    public AdviceController(Environment env) {
        this.env = env;
    }

    @ModelAttribute("siteUrl")
    public String siteUrl() {
        return env.getProperty("site.url");
    }

    @ModelAttribute("templateUrl")
    public String templateUrl() {
        return siteUrl() + "/templates/" + env.getProperty("site.template");
    }

    @ModelAttribute("siteName")
    public String siteName() {
        return env.getProperty("site.name");
    }
}
