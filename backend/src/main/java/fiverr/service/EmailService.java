package fiverr.service;

import fiverr.entity.User;
import fiverr.event.PasswordResetEvent;
import fiverr.event.RegistrationEvent;

public interface EmailService {

    void sendEmailValidation(RegistrationEvent event);

    void sendPasswordReset(PasswordResetEvent event);
}
