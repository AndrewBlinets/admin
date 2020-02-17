package by.ipps.admin.utils.resttemplate.impl;

import by.ipps.admin.custom.CustomPage;
import by.ipps.admin.entity.News;
import by.ipps.admin.entity.NewsDtoAdmin;
import by.ipps.admin.utils.resttemplate.NewsRestTemplate;
import by.ipps.admin.utils.resttemplate.base.AbstractBaseEntityRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class NewsTemplate extends AbstractBaseEntityRestTemplate<News> implements NewsRestTemplate {
  @Override
  public ResponseEntity<CustomPage<NewsDtoAdmin>> findPagingRecords(
      long page, int size, String sort, String language, String url) {
    UriComponentsBuilder builder =
        UriComponentsBuilder.fromHttpUrl(URL_SERVER + "news/admin")
            .queryParam("page", String.valueOf(page))
            .queryParam("size", String.valueOf(size))
            .queryParam("sort", sort)
            .queryParam("language", language);
    final ParameterizedTypeReference<CustomPage<NewsDtoAdmin>> responseType =
        new ParameterizedTypeReference<CustomPage<NewsDtoAdmin>>() {};
    return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, responseType);
  }
}
