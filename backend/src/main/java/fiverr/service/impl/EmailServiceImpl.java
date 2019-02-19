package fiverr.service.impl;

import fiverr.event.PasswordResetEvent;
import fiverr.event.RegistrationEvent;
import fiverr.service.EmailService;
import fiverr.util.Translator;
import org.springframework.context.event.EventListener;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender sender;

    public EmailServiceImpl(JavaMailSender sender) {
        this.sender = sender;
    }

    @Override
    @EventListener
    public void sendEmailValidation(RegistrationEvent event) {
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
    }

    @Override
    @EventListener
    public void sendPasswordReset(PasswordResetEvent event) {
        LocaleContextHolder.setLocale(event.getLocale());

    }
}
