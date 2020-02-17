package by.ipps.admin.controller.base;

import by.ipps.admin.custom.CustomPage;
import by.ipps.admin.entity.BaseEntity;
import by.ipps.admin.entity.UserAuth;
import by.ipps.admin.utils.RestRequestToDao;
import by.ipps.admin.utils.resttemplate.base.BaseEntityRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

public abstract class BaseEntityAbstractController<
        T extends BaseEntity, S extends BaseEntityRestTemplate<T>>
    implements BaseEntityController<T> {

  protected final S baseEntityTemplate;
  protected String url;

  @Autowired private RestRequestToDao restRequestToDao;

  protected BaseEntityAbstractController(S s, String url) {
    this.baseEntityTemplate = s;
    this.url = url;
  }

  @CrossOrigin
  @Override
  public ResponseEntity<T> get(Long id, String language) {
    return baseEntityTemplate.findById(id, url);
  }

  @CrossOrigin
  @Override
  public ResponseEntity<T> create(T entity) {
    long idUser = getUserID();
    return baseEntityTemplate.create(entity, url, idUser);
  }

  private Long getUserID() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = "";
    if (principal instanceof UserDetails) {
      username = ((UserDetails) principal).getUsername();
    } else {
      username = principal.toString();
    }
    UserAuth user = restRequestToDao.getUserByLogin(username);
    return user.getId();
  }

  @CrossOrigin
  @Override
  public ResponseEntity<T> update(T entity) {
    long idUser = getUserID();
    return baseEntityTemplate.update(entity, url, idUser);
  }

  @CrossOrigin
  @Override
  public ResponseEntity<Boolean> remove(Long id) {
    long idUser = getUserID();
    return baseEntityTemplate.delete(id, url, idUser);
  }

  @CrossOrigin
  @Override
  public ResponseEntity<CustomPage<T>> getAll(long page, int size, String sort, String language) {
    return baseEntityTemplate.findPagingRecords(page, size, sort, language, url);
  }

  @CrossOrigin
  @Override
  public ResponseEntity<List<T>> getAll(String language) {
    return baseEntityTemplate.findAllEntity(language, url);
  }
}
