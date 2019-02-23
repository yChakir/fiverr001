package fiverr.event;

import fiverr.entity.Token;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Data
public class RegistrationEvent extends ApplicationEvent {

    private Token token;

    private Locale locale;

    public RegistrationEvent(Object source, Token token, Locale locale) {
        super(source);
        this.token = token;
        this.locale = locale;
    }
}
