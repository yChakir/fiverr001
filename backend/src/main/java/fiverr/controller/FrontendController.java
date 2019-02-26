package fiverr.controller;

import org.springframework.web.bind.annotation.GetMapping;

//@Controller
//@RequestMapping("/*")
public class FrontendController {
    @GetMapping
    public String vue() {
        return "forward:index.html";
    }
}
