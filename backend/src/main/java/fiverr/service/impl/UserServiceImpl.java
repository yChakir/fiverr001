package fiverr.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fiverr.entity.Image;
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
import fiverr.vos.EditProfile;
import fiverr.vos.EmailValidation;
import fiverr.vos.Registration;
import fiverr.vos.ResetPassword;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final TokenService tokenService;

    private final PasswordEncoder encoder;

    private final ApplicationEventPublisher publisher;

    public UserServiceImpl(
            UserRepository repository,
            TokenService tokenService,
            PasswordEncoder encoder,
            ApplicationEventPublisher publisher
    ) {
        this.repository = repository;
        this.tokenService = tokenService;
        this.encoder = encoder;
        this.publisher = publisher;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("loadUserByUsername() :: username = {}", username);
        User user = repository.findByEmail(username).orElseThrow(() -> {
            log.warn("loadUserByUsername() :: user not found, username = {}", username);
            return new UsernameNotFoundException(Translator.translate("exception.account.notFound"));
        });

        if (!user.isActive()) {
            log.warn("loadUserByUsername() :: the user '{}' is not active.", username);
            throw new ClientException(Translator.translate("exception.auth.account-not-active"));
        }

        log.debug("loadUserByUsername() :: the user '{}' found and active.", username);
        return user;
    }

    @Override
    public User register(Registration registration) {
        log.debug("register() :: registration = {}", registration);
        Optional<User> alreadyExit = repository.findByEmail(registration.getEmail());

        if (alreadyExit.isPresent()) {
            log.warn("register() :: registration already exist: {}", registration.getEmail());
            throw new ClientException(Translator.translate("exception.account.alreadyExist"));
        }

        User user = new User();
        user.setName(registration.getName());
        user.setSurname(registration.getSurname());
        user.setEmail(registration.getEmail());
        user.setPassword(encoder.encode(registration.getPassword()));

        log.debug("register() :: saving user: {}", user);
        user = repository.save(user);

        if (user != null) {
            Token token = tokenService.saveForUser(user, TokenType.REGISTRATION);
            log.debug("register() :: saving token: {}", token);

            publisher.publishEvent(new RegistrationEvent(this, token, LocaleContextHolder.getLocale()));
        }

        return user;
    }

    @Override
    public void emailValidation(EmailValidation emailValidation) {
        log.debug("emailValidation() :: emailValidation = {}", emailValidation);
        Optional<User> optionalUser = repository.findByEmail(emailValidation.getEmail());

        log.debug("emailValidation() :: check user exists: {}", emailValidation.getEmail());
        User user = optionalUser.orElseThrow(() -> {
            log.debug("emailValidation() :: check user exists: {}", emailValidation.getEmail());
            return new ClientException(Translator.translate("exception.token.invalidToken"));
        });

        Optional<Token> tokenOptional = tokenService.findByUserAndTokenAndType(user, emailValidation.getToken(), TokenType.REGISTRATION);

        log.debug("emailValidation() :: check token exists: {}", emailValidation.getToken());
        Token token = tokenOptional.orElseThrow(() -> new ClientException(Translator.translate("exception.token.invalidToken")));

        log.debug("emailValidation() :: check token is used: {}, used = {}", emailValidation.getToken(), token.isUsed());
        if (token.isUsed()) {
            throw new ClientException(Translator.translate("exception.token.alreadyUsed"));
        }

        log.debug("emailValidation() :: check token expired: {}, used = {}", emailValidation.getToken(), token.getExpireDate().isBefore(LocalDateTime.now()));
        if (token.getExpireDate().isBefore(LocalDateTime.now())) {
            throw new ClientException(Translator.translate("exception.token.tokenExpired"));
        }


        log.debug("emailValidation() :: updating and saving user and token...");
        token.setUsed(true);
        user.setActive(true);

        tokenService.save(token);
        repository.save(user);
    }

    @Override
    public void forgotPassword(String email) {
        log.debug("forgotPassword() :: email = {}", email);
        Optional<User> optionalUser = repository.findByEmail(email);

        log.debug("forgotPassword() :: check if user present email = {}, present = {}", email, optionalUser.isPresent());
        optionalUser.ifPresent(user -> {
            List<Token> tokens = tokenService.findAllByUserAndTypeAndNotUsed(user, TokenType.PASSWORD_RESET);

            log.debug("forgotPassword() :: check if there is already a valid and unused token for the user.");
            Optional<Token> optionalToken = tokens.stream()
                    .filter(token -> token.getExpireDate().isAfter(LocalDateTime.now()))
                    .findAny();

            Token token;
            if (optionalToken.isPresent()) {
                log.debug("forgotPassword() :: found an valid token in the database, update expiration date.");
                token = optionalToken.get();
                token.setExpireDate(LocalDateTime.now().plusDays(1));
            } else {
                log.debug("forgotPassword() :: no valid token found, create a new one.");
                token = new Token(user, TokenType.PASSWORD_RESET);
            }

            tokenService.save(token);

            publisher.publishEvent(new PasswordResetEvent(this, token, LocaleContextHolder.getLocale()));
        });
    }

    @Override
    public void resetPassword(ResetPassword resetPassword) {
        log.debug("resetPassword() :: resetPassword = {}", resetPassword);
        Optional<User> optionalUser = repository.findByEmail(resetPassword.getEmail());

        log.debug("resetPassword() :: check if user exist.");
        User user = optionalUser.orElseThrow(() -> new ClientException(Translator.translate("exception.token.invalidToken")));

        Optional<Token> tokenOptional = tokenService.findByUserAndTokenAndType(user, resetPassword.getToken(), TokenType.PASSWORD_RESET);

        log.debug("resetPassword() :: check if token exist.");
        Token token = tokenOptional.orElseThrow(() -> new ClientException(Translator.translate("exception.token.invalidToken")));

        log.debug("resetPassword() :: check if token is used.");
        if (token.isUsed()) {
            throw new ClientException(Translator.translate("exception.token.alreadyUsed"));
        }

        log.debug("resetPassword() :: check if token expired.");
        if (token.getExpireDate().isBefore(LocalDateTime.now())) {
            throw new ClientException(Translator.translate("exception.token.tokenExpired"));
        }

        log.debug("resetPassword() :: token is valid, update and save user and token.");
        token.setUsed(true);
        user.setPassword(encoder.encode(resetPassword.getPassword()));

        tokenService.save(token);
        repository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        log.debug("findByEmail() :: email = {}", email);
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(Translator.translate("exception.account.notFound")));
    }

    @Override
    public void changePassword(String email, ChangePassword changePassword) {
        log.debug("changePassword() :: email = {}, changePassword = {}", email, changePassword);

        log.debug("changePassword() :: assert that new password match confirmation.");
        if (!changePassword.getConfirmation().equals(changePassword.getNewPassword())) {
            throw new ClientException(Translator.translate("exception.change-password.notMatch"));
        }

        log.debug("changePassword() :: assert that new password do not match old one.");
        if (changePassword.getNewPassword().equals(changePassword.getCurrentPassword())) {
            throw new ClientException(Translator.translate("exception.change-password.matchOld"));
        }

        User user = findByEmail(email);

        log.debug("changePassword() :: assert that old password is valid.");
        if (!encoder.matches(changePassword.getCurrentPassword(), user.getPassword())) {
            throw new ClientException(Translator.translate("exception.change-password.wrongPassword"));
        }

        log.debug("changePassword() :: all test passed, changing the password.");
        user.setPassword(encoder.encode(changePassword.getNewPassword()));

        repository.save(user);
    }

    @Override
    public User editProfile(String email, EditProfile editProfile) {
        log.debug("editProfile() :: email = {}, editProfile = {}", email, editProfile);
        User user = findByEmail(email);

        user.setName(editProfile.getName());
        user.setSurname(editProfile.getSurname());
        user.setPhone(editProfile.getPhone());

        return repository.save(user);
    }

    @Override
    public void setAvatar(String email, Image image) {
        log.debug("setAvatar() :: email = {}, name = {}", email, image.getName());

        User user = findByEmail(email);

        user.setAvatar(image);

        repository.save(user);
    }
}
