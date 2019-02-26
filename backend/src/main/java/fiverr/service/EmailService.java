package fiverr.service;

import fiverr.event.ContactEvent;
import fiverr.event.PasswordResetEvent;
import fiverr.event.RegistrationEvent;

public interface EmailService {

    void sendEmailValidation(RegistrationEvent event);

    void sendPasswordReset(PasswordResetEvent event);

    void sendContact(ContactEvent event);
}
