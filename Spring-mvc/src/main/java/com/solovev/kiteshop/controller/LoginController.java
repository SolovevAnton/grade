package com.solovev.kiteshop.controller;

import com.solovev.kiteshop.common.APINamings;
import com.solovev.kiteshop.common.TemplatesNamings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(APINamings.LOGIN)
public class LoginController {
    @GetMapping
    public String getLogin() {
        return TemplatesNamings.LOGIN;
    }
}
