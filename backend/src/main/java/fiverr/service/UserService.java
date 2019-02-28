package fiverr.service;

import fiverr.entity.User;
import fiverr.vos.*;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(Registration registration);

    void emailValidation(EmailValidation emailValidation);

    void forgotPassword(String email);

    void resetPassword(ResetPassword resetPassword);

    User findByEmail(String email);

    void changePassword(String email, ChangePassword changePassword);

    User editProfile(String email, EditProfile editProfile);
}
