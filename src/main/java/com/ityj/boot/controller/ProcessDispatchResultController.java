package com.ityj.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProcessDispatchResultController {

    /**
     * 不能直接返回 forward:success来跳转到success.html，因为他的视图解析走的是AbstractView.render()
     * 不走thymeleaf的前缀和后缀规则。走的是  spring.web.resources.static-locations=classpath:/aa/  默认资源路径是/aa/
     * @param model
     * @return
     */
    @GetMapping("/fwd")
    public String forwardPage(Model model) {
        model.addAttribute("msg", "test forward");
        return "forward:iNdex.html";  // 访问aa/index.html，大小写不敏感
    }

    // AbstractView.render()
    @GetMapping("/red")
    public String redirect(Model model) {
        model.addAttribute("msg", "test redirect");
        return "redirect:/suc";  // 响应重定向，两次请求1、/red 2、/suc 。地址栏变成第二次请求的/suc
    }

    /**
     * 直接返回字符串不带forward，会走thymeleafView.render() 所以可以进行页面跳转
     * 服务器间进行一次请求，和forward不同
     *
     * @param model
     * @return
     */
    @GetMapping("/suc")
    public String suc(Model model) {
        model.addAttribute("param", "test");
        return "success";  // 访问templates/success.html
    }

}
