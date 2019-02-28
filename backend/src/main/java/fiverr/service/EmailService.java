package fiverr.service;

import fiverr.event.ContactEvent;
import fiverr.event.PasswordResetEvent;
import fiverr.event.RegistrationEvent;
import fiverr.pojo.EmailType;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendEmailValidation(RegistrationEvent event);

    void sendPasswordReset(PasswordResetEvent event);

    void sendContact(ContactEvent event);

    void sendAndSave(SimpleMailMessage message, EmailType type);
}
