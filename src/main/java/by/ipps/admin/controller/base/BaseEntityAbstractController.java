package by.ipps.admin.controller.base;

import by.ipps.admin.custom.CustomPage;
import by.ipps.admin.entity.BaseEntity;
import by.ipps.admin.utils.resttemplate.base.BaseEntityRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

public abstract class BaseEntityAbstractController<T extends BaseEntity, S extends BaseEntityRestTemplate<T>>
    implements BaseEntityController<T> {

  protected final S baseEntityTemplate;
  protected final String url;

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
    return baseEntityTemplate.create(entity, url);
  }

  @CrossOrigin
  @Override
  public ResponseEntity<T> update(T entity) {
    return baseEntityTemplate.update(entity, url);
  }

  @CrossOrigin
  @Override
  public ResponseEntity<Boolean> remove(Long id) {
    return baseEntityTemplate.delete(id, url);
  }

  @CrossOrigin
  @Override
  public ResponseEntity<CustomPage<T>> getAll(long page, int size, String sort, String language) {
    return baseEntityTemplate.findPagingRecords(page, size, sort, language, url);
  }

}
