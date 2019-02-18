package fiverr.service;

import fiverr.entity.User;
import fiverr.vos.EmailValidation;
import fiverr.vos.Registration;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(Registration registration);

    void emailValidation(EmailValidation emailValidation);
}
