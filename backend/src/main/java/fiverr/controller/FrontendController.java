package fiverr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class FrontendController {
    @GetMapping
    public String index() {
        return "forward:index.html";
    }
}
