package by.ipps.admin.controller;

import by.ipps.admin.controller.base.BaseEntityAbstractController;
import by.ipps.admin.controller.base.BaseEntityController;
import by.ipps.admin.entity.Section;
import by.ipps.admin.utils.resttemplate.SectionRestTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/section")
public class SectionController extends BaseEntityAbstractController<Section, SectionRestTemplate>
    implements BaseEntityController<Section> {
  protected SectionController(SectionRestTemplate sectionRestTemplate) {
    super(sectionRestTemplate, "section/", "id");
  }
}
