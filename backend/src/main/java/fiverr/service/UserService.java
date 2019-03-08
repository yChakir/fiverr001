package fiverr.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import fiverr.entity.Image;
import fiverr.entity.User;
import fiverr.vos.ChangePassword;
import fiverr.vos.EditProfile;
import fiverr.vos.EmailValidation;
import fiverr.vos.Registration;
import fiverr.vos.ResetPassword;

public interface UserService extends UserDetailsService {
    User register(Registration registration);

    void emailValidation(EmailValidation emailValidation);

    void forgotPassword(String email);

    void resetPassword(ResetPassword resetPassword);

    User findByEmail(String email);

    void changePassword(String email, ChangePassword changePassword);

    User editProfile(String email, EditProfile editProfile);

    void setAvatar(String email, Image image);
}
