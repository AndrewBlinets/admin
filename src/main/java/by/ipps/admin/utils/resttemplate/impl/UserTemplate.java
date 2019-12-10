package by.ipps.admin.utils.resttemplate.impl;

import by.ipps.admin.entity.User;
import by.ipps.admin.utils.resttemplate.UserRestTemplate;
import by.ipps.admin.utils.resttemplate.base.AbstractBaseEntityRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserTemplate extends AbstractBaseEntityRestTemplate<User> implements UserRestTemplate {


    @Override
    public User getUserByLogin(String login) {
        ResponseEntity<User> response = restTemplate.postForEntity( URL_SERVER + "users/auth",
                login, User.class);
        return response.getBody();
    }
}
