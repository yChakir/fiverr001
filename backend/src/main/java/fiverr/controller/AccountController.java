package fiverr.controller;

import fiverr.vos.EmailValidation;
import fiverr.vos.Registration;
import fiverr.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static fiverr.configuration.AppConstants.REGISTER;

@RestController
@RequestMapping("api/v1/account")
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody @Valid Registration registration) {
        userService.register(registration);

        return ResponseEntity.created(null).build();
    }

    @PostMapping("email-validation")
    public ResponseEntity emailValidation(@RequestBody @Valid EmailValidation emailValidation) {
        userService.emailValidation(emailValidation);

        return ResponseEntity.created(null).build();
    }
}
