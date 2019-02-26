package fiverr.service.impl;

import fiverr.entity.Contact;
import fiverr.event.ContactEvent;
import fiverr.repository.ContactRepository;
import fiverr.service.ContactService;
import fiverr.vos.ContactRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository repository;

    private final ConversionService conversionService;

    private final ApplicationEventPublisher publisher;

    public ContactServiceImpl(ContactRepository repository, ConversionService conversionService, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.conversionService = conversionService;
        this.publisher = publisher;
    }

    @Override
    public Contact contact(ContactRequest contactRequest) {
        Contact toSave = conversionService.convert(contactRequest, Contact.class);
        toSave = repository.save(toSave);

        if (toSave != null) {
            publisher.publishEvent(new ContactEvent(this, toSave, LocaleContextHolder.getLocale()));
        }

        return toSave;
    }
}
