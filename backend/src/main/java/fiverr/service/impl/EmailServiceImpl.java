package fiverr.service.impl;

import fiverr.event.ContactEvent;
import fiverr.event.PasswordResetEvent;
import fiverr.event.RegistrationEvent;
import fiverr.service.EmailService;
import fiverr.util.Translator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${app.mail.contact-email}")
    private String contactEmail;

    private final JavaMailSender sender;

    public EmailServiceImpl(JavaMailSender sender) {
        this.sender = sender;
    }

    @Override
    @EventListener
    public void sendEmailValidation(RegistrationEvent event) {
        log.debug("sendEmailValidation() :: Sending registration email for: {}", event);
        LocaleContextHolder.setLocale(event.getLocale());
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(event.getToken().getUser().getEmail());
        message.setSubject(Translator.translate("email.registration.title"));
        message.setText(Translator.translate(
                "email.registration.body",
                event.getToken().getUser().getName(),
                event.getToken().getUser().getSurname(),
                event.getToken().getUser().getEmail(),
                event.getToken().getToken()
                )
        );

        sender.send(message);
        log.debug("sendEmailValidation() :: Sending registration email for: {} - End", event);
    }

    @Override
    @EventListener
    public void sendPasswordReset(PasswordResetEvent event) {
        log.debug("sendPasswordReset() :: Sending reset password email for: {}", event);
        LocaleContextHolder.setLocale(event.getLocale());
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(event.getToken().getUser().getEmail());
        message.setSubject(Translator.translate("email.forgot-password.title"));
        message.setText(Translator.translate(
                "email.forgot-password.body",
                event.getToken().getUser().getName(),
                event.getToken().getUser().getSurname(),
                event.getToken().getUser().getEmail(),
                event.getToken().getToken()
                )
        );

        sender.send(message);
        log.debug("sendPasswordReset() :: Sending reset password email for: {} - End", event);
    }

    @Override
    @EventListener
    public void sendContact(ContactEvent event) {
        log.debug("sendContact() :: Sending contact email for: {}", event);
        LocaleContextHolder.setLocale(event.getLocale());

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(contactEmail);
        message.setSubject(String.format("New contact message from %s", event.getContact().getName()));
        message.setText(String.format(
                "Hello,\n\n" +
                        "You have a new contact message.\n\n" +
                        "\t- From: %s\n" +
                        "\t- Email: %s\n" +
                        "\t- Date: %s\n" +
                        "\t- Message: %s\n\n" +
                        "Thank you.",
                event.getContact().getName(),
                event.getContact().getEmail(),
                event.getContact().getCreated(),
                event.getContact().getMessage()
                )
        );

        sender.send(message);

        log.debug("sendContact() :: Sending contact email for: {} - End", event);
    }
}
