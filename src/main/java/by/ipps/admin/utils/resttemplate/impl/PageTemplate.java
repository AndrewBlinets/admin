package by.ipps.admin.utils.resttemplate.impl;

import by.ipps.admin.custom.CustomPage;
import by.ipps.admin.entity.Page;
import by.ipps.admin.utils.resttemplate.PageRestTemplate;
import by.ipps.admin.utils.resttemplate.base.AbstractBaseEntityRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class PageTemplate extends AbstractBaseEntityRestTemplate<Page> implements PageRestTemplate {

  @Override
  public ResponseEntity<CustomPage<Page>> findPagingRecords(
      long page, int size, String sort, String language, String url) {
    UriComponentsBuilder builder =
        UriComponentsBuilder.fromHttpUrl(URL_SERVER + url + "/admin")
            .queryParam("page", String.valueOf(page))
            .queryParam("size", String.valueOf(size))
            .queryParam("sort", sort)
            .queryParam("language", language);
    final ParameterizedTypeReference<CustomPage<Page>> responseType =
        new ParameterizedTypeReference<CustomPage<Page>>() {};
    return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, responseType);
  }

  @Override
  public ResponseEntity<Page> findById(Long id, String url) {
    UriComponentsBuilder builder =
        UriComponentsBuilder.fromHttpUrl(URL_SERVER + url + "/admin/" + id);
    final ParameterizedTypeReference<Page> responseType = new ParameterizedTypeReference<Page>() {};
    return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, responseType);
  }
}
