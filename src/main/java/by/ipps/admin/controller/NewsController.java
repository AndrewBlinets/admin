package by.ipps.admin.controller;

import by.ipps.admin.controller.base.BaseEntityAbstractController;
import by.ipps.admin.controller.base.BaseEntityController;
import by.ipps.admin.entity.News;
import by.ipps.admin.utils.resttemplate.DepartmentRestTemplate;
import by.ipps.admin.utils.resttemplate.NewsRestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class NewsController extends BaseEntityAbstractController<News, NewsRestTemplate>
        implements BaseEntityController<News> {
//
//    private final NewsService service;
//    private ModelMapper mapper;
//
public NewsController(NewsRestTemplate newsRestTemplate) {
  super(newsRestTemplate, "news");
}
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @GetMapping("/client")
//    public ResponseEntity<PageNewsDto> getAllForClient(
//            @PageableDefault() Pageable pageable,
//            @RequestParam(value = "language", required = false, defaultValue = "ru") String language) {
//        entityManager.unwrap(Session.class).enableFilter(FilterName.LANGUAGE).setParameter("language", language);
//        Page<News> news = service.findPagingRecordsForClient(pageable);
//        PageNewsDto newsDto = mapper.map(news, PageNewsDto.class);
//        entityManager.unwrap(Session.class).disableFilter(FilterName.LANGUAGE);
//        return new ResponseEntity<>(newsDto, news != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
//    }
//
//    @Transactional
//    @GetMapping("/client/{id}")
//    public ResponseEntity<NewsDtoFull> getByIdForClient(
//            @PathVariable Long id,
//            @RequestParam(value = "language", required = false, defaultValue = "ru") String language) {
//        entityManager.unwrap(Session.class).enableFilter(FilterName.LANGUAGE).setParameter("language", language);
//        News news = service.findByIdForClient(id);
//        if(news != null) {
//            NewsDtoFull newsF = mapper.map(news, NewsDtoFull.class);
//            entityManager.unwrap(Session.class).disableFilter(FilterName.LANGUAGE);
//            return new ResponseEntity<>(newsF, HttpStatus.OK);
//        } else {
//            entityManager.unwrap(Session.class).disableFilter(FilterName.LANGUAGE);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
}
