package fiverr.converter;

import fiverr.entity.Contact;
import fiverr.vos.ContactRequest;
import org.springframework.core.convert.converter.Converter;

public class ContactRequestToContact implements Converter<ContactRequest, Contact> {
    @Override
    public Contact convert(ContactRequest source) {
        Contact result = new Contact();

        result.setEmail(source.getEmail());
        result.setName(source.getName());
        result.setMessage(source.getMessage());

        return result;
    }
}
