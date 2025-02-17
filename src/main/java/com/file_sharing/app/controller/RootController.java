package com.file_sharing.app.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@AllArgsConstructor
public class RootController {
    @GetMapping("/")
    public String root() {
        return "redirect:/swagger-ui.html";
    }
}
