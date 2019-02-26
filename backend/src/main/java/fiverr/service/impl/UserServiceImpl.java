package fiverr.service.impl;

import fiverr.entity.Token;
import fiverr.entity.User;
import fiverr.event.PasswordResetEvent;
import fiverr.event.RegistrationEvent;
import fiverr.exception.ClientException;
import fiverr.pojo.TokenType;
import fiverr.repository.UserRepository;
import fiverr.service.TokenService;
import fiverr.service.UserService;
import fiverr.util.Translator;
import fiverr.vos.ChangePassword;
import fiverr.vos.EmailValidation;
import fiverr.vos.Registration;
import fiverr.vos.ResetPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final TokenService tokenService;

    private final PasswordEncoder encoder;

    private final ApplicationEventPublisher publisher;

    public UserServiceImpl(UserRepository userRepository, TokenService tokenService, PasswordEncoder encoder, ApplicationEventPublisher publisher) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.encoder = encoder;
        this.publisher = publisher;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(Translator.translate("exception.account.notFound")));

        if (!user.isActive()) {
            throw new ClientException(Translator.translate("exception.auth.account-not-active"));
        }

        return user;
    }

    @Override
    public User register(Registration registration) {
        log.debug("New registration: {}", registration);
        Optional<User> alreadyExit = userRepository.findByEmail(registration.getEmail());

        if (alreadyExit.isPresent()) {
            throw new ClientException(Translator.translate("exception.account.alreadyExist"));
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

        User user = optionalUser.orElseThrow(() -> new ClientException(Translator.translate("exception.token.invalidToken")));

        Optional<Token> tokenOptional = tokenService.findByUserAndTokenAndType(user, emailValidation.getToken(), TokenType.REGISTRATION);

        Token token = tokenOptional.orElseThrow(() -> new ClientException(Translator.translate("exception.token.invalidToken")));

        if (token.isUsed()) {
            throw new ClientException(Translator.translate("exception.token.alreadyUsed"));
        }

        if (token.getExpireDate().isBefore(LocalDateTime.now())) {
            throw new ClientException(Translator.translate("exception.token.tokenExpired"));
        }

        token.setUsed(true);
        user.setActive(true);

        tokenService.save(token);
        userRepository.save(user);
    }

    @Override
    public void forgotPassword(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        optionalUser.ifPresent(user -> {
            List<Token> tokens = tokenService.findAllByUserAndTypeAndNotUsed(user, TokenType.PASSWORD_RESET);

            Optional<Token> optionalToken = tokens.stream()
                    .filter(token -> token.getExpireDate().isAfter(LocalDateTime.now()))
                    .findAny();

            Token token;
            if (optionalToken.isPresent()) {
                token = optionalToken.get();
                token.setExpireDate(LocalDateTime.now().plusDays(1));
            } else {
                token = new Token(user, TokenType.PASSWORD_RESET);
            }

            tokenService.save(token);

            publisher.publishEvent(new PasswordResetEvent(this, token, LocaleContextHolder.getLocale()));
        });
    }

    @Override
    public void resetPassword(ResetPassword resetPassword) {
        Optional<User> optionalUser = userRepository.findByEmail(resetPassword.getEmail());

        User user = optionalUser.orElseThrow(() -> new ClientException(Translator.translate("exception.token.invalidToken")));

        Optional<Token> tokenOptional = tokenService.findByUserAndTokenAndType(user, resetPassword.getToken(), TokenType.PASSWORD_RESET);

        Token token = tokenOptional.orElseThrow(() -> new ClientException(Translator.translate("exception.token.invalidToken")));

        if (token.isUsed()) {
            throw new ClientException(Translator.translate("exception.token.alreadyUsed"));
        }

        if (token.getExpireDate().isBefore(LocalDateTime.now())) {
            throw new ClientException(Translator.translate("exception.token.tokenExpired"));
        }

        token.setUsed(true);
        user.setPassword(encoder.encode(resetPassword.getPassword()));

        tokenService.save(token);
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String name) {
        return userRepository.findByEmail(name)
                .orElseThrow(() -> new UsernameNotFoundException(Translator.translate("exception.account.notFound")));
    }

    @Override
    public void changePassword(String email, ChangePassword changePassword) {
        if (!changePassword.getConfirmation().equals(changePassword.getNewPassword())) {
            throw new ClientException(Translator.translate("exception.change-password.notMatch"));
        }

        if (changePassword.getNewPassword().equals(changePassword.getCurrentPassword())) {
            throw new ClientException(Translator.translate("exception.change-password.matchOld"));
        }

        User user = findByEmail(email);

        if (!encoder.matches(changePassword.getCurrentPassword(), user.getPassword())) {
            throw new ClientException(Translator.translate("exception.change-password.wrongPassword"));
        }

        user.setPassword(encoder.encode(changePassword.getNewPassword()));

        userRepository.save(user);
    }
}
