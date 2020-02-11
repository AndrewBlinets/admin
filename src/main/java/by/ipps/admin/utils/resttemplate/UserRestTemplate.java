package by.ipps.admin.utils.resttemplate;

import by.ipps.admin.entity.User;
import by.ipps.admin.utils.resttemplate.base.BaseEntityRestTemplate;
import org.springframework.stereotype.Component;

@Component
public interface UserRestTemplate extends BaseEntityRestTemplate<User> {
  public User getUserByLogin(String login);
}
