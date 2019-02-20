package fiverr.controller;

import fiverr.entity.User;
import fiverr.service.UserService;
import fiverr.vos.Profile;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.annotation.MultipartConfig;
import java.security.Principal;

@RestController
@MultipartConfig
@RequestMapping("api/v1/profile")
@PreAuthorize("isAuthenticated()")
public class ProfileController {

    private final UserService userService;

    private final ConversionService conversionService;

    public ProfileController(UserService userService, ConversionService conversionService) {
        this.userService = userService;
        this.conversionService = conversionService;
    }

    @GetMapping
    public ResponseEntity<Profile> getPrinciple(Principal principal) {
        User user = userService.findByEmail(principal.getName());

        Profile profile = conversionService.convert(user, Profile.class);

        return ResponseEntity.ok(profile);
    }
}
