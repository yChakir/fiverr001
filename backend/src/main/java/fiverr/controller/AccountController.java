package fiverr.controller;

import fiverr.service.UserService;
import fiverr.vos.EmailValidation;
import fiverr.vos.Registration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
