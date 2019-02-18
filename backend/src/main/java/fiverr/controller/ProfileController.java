package fiverr.controller;

import org.springframework.web.bind.annotation.*;
import javax.servlet.annotation.MultipartConfig;

import static fiverr.configuration.AppConstants.PROFILE;

@RestController
@MultipartConfig
@RequestMapping(PROFILE)
public class ProfileController {
}
