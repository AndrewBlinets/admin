package by.ipps.admin.utils.resttemplate.base;

import by.ipps.admin.custom.CustomPage;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BaseEntityRestTemplate<T> {

  ResponseEntity<T> findById(Long id, String url);

  ResponseEntity<CustomPage<T>> findPagingRecords(
      long page, int size, String sort, String language, String url);

  ResponseEntity<T> create(T entity, String url, long idUser);

  ResponseEntity<T> update(T entity, String url, long idUser);

  ResponseEntity<Boolean> delete(long byId, String url, long idUser);

  ResponseEntity<List<T>> findAllEntity(String language, String url);
}
