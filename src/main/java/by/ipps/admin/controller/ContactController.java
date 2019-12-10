package by.ipps.admin.controller;

import by.ipps.admin.controller.base.BaseEntityAbstractController;
import by.ipps.admin.controller.base.BaseEntityController;
import by.ipps.admin.entity.Contact;
import by.ipps.admin.utils.resttemplate.ContactRestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
public class ContactController extends BaseEntityAbstractController<Contact, ContactRestTemplate>
        implements BaseEntityController<Contact> {
    protected ContactController(ContactRestTemplate contactRestTemplate) {
        super(contactRestTemplate, "/contact");
    }
}
