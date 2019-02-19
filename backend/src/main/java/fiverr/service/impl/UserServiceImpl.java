package fiverr.service.impl;

import fiverr.entity.Token;
import fiverr.entity.User;
import fiverr.event.RegistrationEvent;
import fiverr.exception.ResourceNotFoundException;
import fiverr.exception.ServiceException;
import fiverr.pojo.TokenType;
import fiverr.repository.UserRepository;
import fiverr.service.EmailService;
import fiverr.service.TokenService;
import fiverr.service.UserService;
import fiverr.util.Translator;
import fiverr.vos.EmailValidation;
import fiverr.vos.Registration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final TokenService tokenService;

    private final PasswordEncoder encoder;

    private final ApplicationEventPublisher publisher;

    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, TokenService tokenService, PasswordEncoder encoder, ApplicationEventPublisher publisher, EmailService emailService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.encoder = encoder;
        this.publisher = publisher;
        this.emailService = emailService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException(Translator.translate("exception.account.notFound")));
    }

    @Override
    public User register(Registration registration) {
        Optional<User> alreadyExit = userRepository.findByEmail(registration.getEmail());

        if (alreadyExit.isPresent()) {
            throw new ServiceException(Translator.translate("exception.account.alreadyExist"));
        }

        User user = new User();
        user.setName(registration.getName());
        user.setSurname(registration.getSurname());
        user.setEmail(registration.getEmail());
        user.setPassword(encoder.encode(registration.getPassword()));

        user = userRepository.save(user);

        if (user != null) {
            Token token = tokenService.saveForUser(user, TokenType.REGISTRATION);

            publisher.publishEvent(new RegistrationEvent(this, token, LocaleContextHolder.getLocale()));
        }

        return user;
    }

    @Override
    public void emailValidation(EmailValidation emailValidation) {
        Optional<User> optionalUser = userRepository.findByEmail(emailValidation.getEmail());

        User user = optionalUser.orElseThrow(() -> new ServiceException(Translator.translate("exception.token.invalidToken")));

        Optional<Token> tokenOptional = tokenService.findByUserAndToken(user, emailValidation.getToken());

        Token token = tokenOptional.orElseThrow(() -> new ServiceException(Translator.translate("exception.token.invalidToken")));

        if (token.isUsed()) {
            throw new ServiceException(Translator.translate("exception.token.alreadyUsed"));
        }

        if (token.getExpireDate().isBefore(LocalDateTime.now())) {
            throw new ServiceException(Translator.translate("exception.token.tokenExpired"));
        }

        token.setUsed(true);
        user.setActive(true);

        tokenService.save(token);
        userRepository.save(user);
    }
}
