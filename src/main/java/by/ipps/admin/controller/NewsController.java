//package by.ipps.admin.controller;//package by.ipps.admin.controller;
//
//import by.ipps.admin.controller.base.BaseEntityAbstractController;
//import by.ipps.admin.controller.base.BaseEntityController;
//import by.ipps.dao.dto.news.NewsDtoFull;
//import by.ipps.dao.dto.news.PageNewsDto;
//import by.ipps.dao.entity.News;
//import by.ipps.dao.service.NewsService;
//import by.ipps.dao.utils.constant.FilterName;
//import org.hibernate.Session;
//import org.modelmapper.ModelMapper;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.transaction.Transactional;
//
//@RestController
//@RequestMapping("/news")
//public class NewsController extends BaseEntityAbstractController<News, NewsService>
//        implements BaseEntityController<News> {
//
//    private final NewsService service;
//    private ModelMapper mapper;
//
//    protected NewsController(NewsService newsService, ModelMapper mapper) {
//        super(newsService, url);
//        this.service = newsService;
//        this.mapper = mapper;
//    }
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
//}
