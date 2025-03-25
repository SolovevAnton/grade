package com.solovev.kiteshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class EntryPointController {
    @GetMapping
    public String index() {
        return "redirect:catalog";
    }
}
