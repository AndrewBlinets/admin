package by.ipps.admin.utils.resttemplate;

import by.ipps.admin.entity.Section;
import by.ipps.admin.utils.resttemplate.base.BaseEntityRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SectionRestTemplate extends BaseEntityRestTemplate<Section> {
  ResponseEntity<List<Section>> findSectionByIdPage(long id);
}
