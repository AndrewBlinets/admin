package by.ipps.admin.utils.resttemplate.impl;

import by.ipps.admin.custom.CustomPage;
import by.ipps.admin.entity.News;
import by.ipps.admin.entity.NewsToBD;
import by.ipps.admin.utils.resttemplate.NewsRestTemplate;
import by.ipps.admin.utils.resttemplate.base.AbstractBaseEntityRestTemplate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@Component
public class NewsTemplate extends AbstractBaseEntityRestTemplate<News> implements NewsRestTemplate {

  @Autowired private ModelMapper modelMapper;

  @Override
  public ResponseEntity<CustomPage<News>> findPagingRecords(
      long page, int size, String sort, String language, String url) {
    UriComponentsBuilder builder =
        UriComponentsBuilder.fromHttpUrl(URL_SERVER + "news/admin")
            .queryParam("page", String.valueOf(page))
            .queryParam("size", String.valueOf(size))
            .queryParam("sort", sort)
            .queryParam("language", language);
    final ParameterizedTypeReference<CustomPage<News>> responseType =
        new ParameterizedTypeReference<CustomPage<News>>() {};
    return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, responseType);
  }

  @Override
  public ResponseEntity<News> create(News entity, String url, long idUser) {
    try {
      UriComponentsBuilder builder =
          UriComponentsBuilder.fromHttpUrl(URL_SERVER + url)
              .queryParam("user", String.valueOf(idUser));
      HttpHeaders requestHeaders = new HttpHeaders();
      requestHeaders.setContentType(MediaType.APPLICATION_JSON);
      requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
      HttpEntity<NewsToBD> requestEntity =
          new HttpEntity<>(modelMapper.map(entity, NewsToBD.class), requestHeaders);
      return restTemplate.exchange(
          builder.toUriString(), HttpMethod.POST, requestEntity, News.class);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<News> update(News entity, String url, long idUser) {
    UriComponentsBuilder builder =
        UriComponentsBuilder.fromHttpUrl(URL_SERVER + url)
            .queryParam("user", String.valueOf(idUser));
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    HttpEntity<NewsToBD> requestEntity =
        new HttpEntity<>(modelMapper.map(entity, NewsToBD.class), requestHeaders);
    return restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, requestEntity, News.class);
  }
}
