package fiverr.service;

import fiverr.entity.Contact;
import fiverr.vos.ContactRequest;

public interface ContactService {
    Contact contact(ContactRequest contactRequest);
}
