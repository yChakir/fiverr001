package fiverr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import static fiverr.configuration.AppConstants.FE_ROUTES;

@Controller
@RequestMapping
public class FrontendController {
    @GetMapping(value = {"","","","","","","",""})
    public String routes() {
        return "forward:index.html";
    }
}
