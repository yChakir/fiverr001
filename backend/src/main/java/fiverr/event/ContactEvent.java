package fiverr.event;

import fiverr.entity.Contact;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Data
public class ContactEvent extends ApplicationEvent {

    private Contact contact;

    private Locale locale;

    public ContactEvent(Object source, Contact contact, Locale locale) {
        super(source);
        this.contact = contact;
        this.locale = locale;
    }
}
