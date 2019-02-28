package fiverr.service.impl;

import fiverr.entity.Email;
import fiverr.event.ContactEvent;
import fiverr.event.PasswordResetEvent;
import fiverr.event.RegistrationEvent;
import fiverr.pojo.EmailType;
import fiverr.repository.EmailRepository;
import fiverr.service.EmailService;
import fiverr.util.Translator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender sender;
    private final EmailRepository emailRepository;
    @Value("${app.mail.contact-email}")
    private String contactEmail;

    public EmailServiceImpl(JavaMailSender sender, EmailRepository emailRepository) {
        this.sender = sender;
        this.emailRepository = emailRepository;
    }

    @Override
    @EventListener
    public void sendEmailValidation(RegistrationEvent event) {
        log.debug("sendEmailValidation() :: event = {}", event);
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

        sendAndSave(message, EmailType.ACCOUNT_CONFIRMATION);
    }

    @Override
    @EventListener
    public void sendPasswordReset(PasswordResetEvent event) {
        log.debug("sendPasswordReset() :: event = {}", event);
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

        sendAndSave(message, EmailType.FORGOT_PASSWORD);
    }

    @Override
    @EventListener
    public void sendContact(ContactEvent event) {
        log.debug("sendContact() :: event = {}", event);
        LocaleContextHolder.setLocale(event.getLocale());
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(event.getContact().getEmail());
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

        sendAndSave(message, EmailType.CONTACT_MESSAGE);
    }

    @Override
    public void sendAndSave(SimpleMailMessage message, EmailType type) {
        Email email = new Email(
                null,
                message.getFrom(),
                Arrays.toString(message.getTo()),
                message.getSubject(),
                message.getText(),
                true,
                type
        );

        try {
            log.debug("sendAndSave() :: Sending email = {}", email);
            sender.send(message);
        } catch (Exception e) {
            log.debug("sendAndSave() :: error while sending email = {}, message = {}", email, e.getMessage());
            email.setSent(false);
        } finally {
            emailRepository.save(email);
            log.debug("sendAndSave() :: Email sent = {}", email);
        }
    }
}
