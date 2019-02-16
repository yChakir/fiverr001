package fiverr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {
    @GetMapping("vue")
    public String vue() {
        return "forward:vue/index.html";
    }
}
