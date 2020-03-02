package by.ipps.admin.utils;

import by.ipps.admin.entity.UserAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestRequestToDao {

  @Value("${url.dao}")
  protected String URL_SERVER;

  public UserAuth getUserByLogin(String login) {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<UserAuth> response =
        restTemplate.postForEntity(URL_SERVER + "users/auth", login, UserAuth.class);
    return response.getBody();
  }
}
