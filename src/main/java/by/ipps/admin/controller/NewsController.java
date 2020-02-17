package by.ipps.admin.controller;

import by.ipps.admin.controller.base.BaseEntityAbstractController;
import by.ipps.admin.controller.base.BaseEntityController;
import by.ipps.admin.custom.CustomPage;
import by.ipps.admin.entity.News;
import by.ipps.admin.entity.NewsDtoAdmin;
import by.ipps.admin.utils.resttemplate.NewsRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
@CrossOrigin
public class NewsController extends BaseEntityAbstractController<News, NewsRestTemplate>
    implements BaseEntityController<News> {

  private final NewsRestTemplate newsRestTemplate;

  public NewsController(NewsRestTemplate newsRestTemplate) {
    super(newsRestTemplate, "news");
    this.newsRestTemplate = newsRestTemplate;
  }

  @Override
  public ResponseEntity<CustomPage<NewsDtoAdmin>> getAll(long page, int size, String sort, String language) {
    return newsRestTemplate.findPagingRecords(page, size, sort, language, url);
  }
}
