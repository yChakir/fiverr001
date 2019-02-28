package fiverr.controller;

import fiverr.entity.User;
import fiverr.service.UserService;
import fiverr.vos.ChangePassword;
import fiverr.vos.EditProfile;
import fiverr.vos.Profile;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.MultipartConfig;
import javax.validation.Valid;
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

    @PostMapping("change-password")
    public ResponseEntity changePassword(
            @RequestBody @Valid ChangePassword changePassword, Principal principal
    ) {
        userService.changePassword(principal.getName(), changePassword);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("edit-profile")
    public ResponseEntity editProfile(
            @RequestBody @Valid EditProfile profile, Principal principal
    ) {
        User user = userService.editProfile(principal.getName(), profile);

        Profile result = conversionService.convert(user, Profile.class);

        return ResponseEntity.ok(result);
    }
}
