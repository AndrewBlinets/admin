package by.ipps.admin.utils.resttemplate.impl;

import by.ipps.admin.entity.Contact;
import by.ipps.admin.utils.resttemplate.ContactRestTemplate;
import by.ipps.admin.utils.resttemplate.base.AbstractBaseEntityRestTemplate;
import org.springframework.stereotype.Component;

@Component
public class ContactTemplate extends AbstractBaseEntityRestTemplate<Contact>
    implements ContactRestTemplate {}
