package fiverr.event;

import fiverr.entity.Token;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class RegistrationEvent extends ApplicationEvent {

    @Getter
    private Token token;

    @Getter
    private Locale locale;

    public RegistrationEvent(Object source, Token token, Locale locale) {
        super(source);
        this.token = token;
        this.locale = locale;
    }
}
