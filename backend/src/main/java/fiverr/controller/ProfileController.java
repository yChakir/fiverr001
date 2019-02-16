package fiverr.controller;

import org.springframework.web.bind.annotation.*;
import javax.servlet.annotation.MultipartConfig;

import static fiverr.pojo.AppConstants.PROFILE;

@RestController
@MultipartConfig
@RequestMapping(PROFILE)
public class ProfileController {
}
