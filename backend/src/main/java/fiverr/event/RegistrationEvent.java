package fiverr.event;

import fiverr.entity.Token;
import fiverr.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class RegistrationEvent extends ApplicationEvent {

    @Getter
    private Token token;

    public RegistrationEvent(Object source, Token token) {
        super(source);
        this.token = token;
    }
}
