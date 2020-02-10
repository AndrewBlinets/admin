package by.ipps.admin.utils.resttemplate.base;

import by.ipps.admin.custom.CustomPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;

public abstract class AbstractBaseEntityRestTemplate<E> implements BaseEntityRestTemplate<E> {

  protected static final String URL_SERVER = "http://localhost:8082/dao/";
//      protected static final String URL_SERVER = "http://192.168.1.125:8080/dao/";
//      protected static final String URL_SERVER = "http://www.ipps.by:5454/dao/";

  private Class<E> entityClass;

  @Autowired protected RestTemplate restTemplate;

  @SuppressWarnings("unchecked")
  public AbstractBaseEntityRestTemplate() {
    this.entityClass =
        (Class<E>)
            ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }

  @Override
  public ResponseEntity<E> findById(Long id, String url) {
    return restTemplate.getForEntity(
        URL_SERVER + url + "/" + id, entityClass, new ParameterizedTypeReference<E>() {});
  }

  @Override
  public ResponseEntity<CustomPage<E>> findPagingRecords(
      long page, int size, String sort, String language, String url) {
    UriComponentsBuilder builder =
        UriComponentsBuilder.fromHttpUrl(URL_SERVER + url)
            .queryParam("page", String.valueOf(page))
            .queryParam("size", String.valueOf(size))
            .queryParam("sort", sort)
            .queryParam("language", language);
    final ParameterizedTypeReference<CustomPage<E>> responseType =
        new ParameterizedTypeReference<CustomPage<E>>() {};
    return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, responseType);
  }

  @Override
  public ResponseEntity<E> create(E entity, String url, long idUser) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_SERVER + url + "?user=" + idUser);
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    HttpEntity<E> requestEntity = new HttpEntity<>(entity, requestHeaders);
    return restTemplate.exchange(
        builder.toUriString(), HttpMethod.POST, requestEntity, entityClass);
  }

  @Override
  public ResponseEntity<E> update(E entity, String url, long idUser) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_SERVER + url + "?user=" + idUser);
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    HttpEntity<E> requestEntity = new HttpEntity<>(entity, requestHeaders);
    return restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, requestEntity, entityClass);
  }

  @Override
  public ResponseEntity<Boolean> delete(long byId, String url, long idUser) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_SERVER + url + "/" + byId + "?user=" + idUser);
    return restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, null, Boolean.TYPE);
  }
}
