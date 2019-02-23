package fiverr.event;

import fiverr.entity.Token;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Data
public class PasswordResetEvent extends ApplicationEvent {

    private Token token;

    private Locale locale;

    public PasswordResetEvent(Object source, Token token, Locale locale) {
        super(source);
        this.token = token;
        this.locale = locale;
    }
}
