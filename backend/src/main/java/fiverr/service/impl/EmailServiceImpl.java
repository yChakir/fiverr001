package fiverr.service.impl;

import fiverr.entity.User;
import fiverr.event.PasswordResetEvent;
import fiverr.event.RegistrationEvent;
import fiverr.service.EmailService;
import org.springframework.context.event.EventListener;
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
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(event.getToken().getUser().getEmail());
        message.setSubject("Email validation");
        message.setText(String.format(
                "Hello %s %s,\n\n" +
                        "Please click the link bellow to complete your registrations.\n" +
                        "http://localhost:8081/emailValidation?email=%s&token=%s",
                event.getToken().getUser().getName(),
                event.getToken().getUser().getSurname(),
                event.getToken().getUser().getEmail(),
                event.getToken().getToken()
        ));

        sender.send(message);
    }

    @Override
    @EventListener
    public void sendPasswordReset(PasswordResetEvent event) {

    }
}
