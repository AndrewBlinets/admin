package by.ipps.admin.controller;

import by.ipps.admin.controller.base.BaseEntityAbstractController;
import by.ipps.admin.controller.base.BaseEntityController;
import by.ipps.admin.entity.News;
import by.ipps.admin.utils.resttemplate.NewsRestTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
@CrossOrigin
public class NewsController extends BaseEntityAbstractController<News, NewsRestTemplate>
    implements BaseEntityController<News> {
  public NewsController(NewsRestTemplate newsRestTemplate) {
    super(newsRestTemplate, "news");
  }
}
