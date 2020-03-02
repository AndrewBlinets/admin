package by.ipps.admin.controller;

import by.ipps.admin.controller.base.BaseEntityAbstractController;
import by.ipps.admin.controller.base.BaseEntityController;
import by.ipps.admin.entity.Project;
import by.ipps.admin.utils.resttemplate.ProjectRestTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
@CrossOrigin
public class ProjectController extends BaseEntityAbstractController<Project, ProjectRestTemplate>
    implements BaseEntityController<Project> {

  protected ProjectController(ProjectRestTemplate projectTemplate) {
    super(projectTemplate, "/project", "dti,ASC");
  }
}
