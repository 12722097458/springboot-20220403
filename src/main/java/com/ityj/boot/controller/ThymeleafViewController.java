package com.ityj.boot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ThymeleafViewController {

    @GetMapping("/succ")
    public String success(Model model) {
        model.addAttribute("msg", "Hello Thymeleaf!");
        model.addAttribute("link", "https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#dialects-the-standard-dialect");
        // ThymeleafProperties可以看到默认的视图位置为classpath:/templates/， 默认的后缀为.html
        return "success";
    }


}
