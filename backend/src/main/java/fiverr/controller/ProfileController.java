package fiverr.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fiverr.entity.User;
import fiverr.service.ImageService;
import fiverr.service.UserService;
import fiverr.vos.ChangePassword;
import fiverr.vos.EditProfile;
import fiverr.vos.Profile;

@RestController
@RequestMapping("api/v1/profile")
@PreAuthorize("isAuthenticated()")
public class ProfileController {

    private final UserService userService;

    private final ImageService imageService;

    private final ConversionService conversionService;

    public ProfileController(UserService userService, ImageService imageService, ConversionService conversionService) {
        this.userService = userService;
        this.imageService = imageService;
        this.conversionService = conversionService;
    }

    @GetMapping
    public ResponseEntity<Profile> getPrinciple(Principal principal) {
        User user = userService.findByEmail(principal.getName());

        Profile profile = conversionService.convert(user, Profile.class);

        return ResponseEntity.ok(profile);
    }

    @PatchMapping("change-password")
    public ResponseEntity changePassword(
            @RequestBody @Valid ChangePassword changePassword, Principal principal
    ) {
        userService.changePassword(principal.getName(), changePassword);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("edit-profile")
    public ResponseEntity editProfile(
            @RequestBody @Valid EditProfile profile, Principal principal
    ) {
        User user = userService.editProfile(principal.getName(), profile);

        Profile result = conversionService.convert(user, Profile.class);

        return ResponseEntity.ok(result);
    }
}
