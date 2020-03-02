package by.ipps.admin.controller;

import by.ipps.admin.controller.base.BaseEntityAbstractController;
import by.ipps.admin.controller.base.BaseEntityController;
import by.ipps.admin.entity.Page;
import by.ipps.admin.utils.resttemplate.PageRestTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/page")
@CrossOrigin
public class PageController extends BaseEntityAbstractController<Page, PageRestTemplate>
    implements BaseEntityController<Page> {

  protected PageController(PageRestTemplate pageRestTemplate) {
    super(pageRestTemplate, "/page", "dti,ASC");
  }
}
