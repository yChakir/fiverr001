package fiverr.service;

import fiverr.entity.User;
import fiverr.vos.ChangePassword;
import fiverr.vos.EmailValidation;
import fiverr.vos.Registration;
import fiverr.vos.ResetPassword;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(Registration registration);

    void emailValidation(EmailValidation emailValidation);

    void forgotPassword(String email);

    void resetPassword(ResetPassword resetPassword);

    User findByEmail(String name);

    void changePassword(String email, ChangePassword changePassword);
}
