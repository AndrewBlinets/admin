package by.ipps.admin.controller;

import by.ipps.admin.controller.base.BaseEntityAbstractController;
import by.ipps.admin.controller.base.BaseEntityController;
import by.ipps.admin.entity.Department;
import by.ipps.admin.utils.resttemplate.DepartmentRestTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
@CrossOrigin
public class DepartmentController
    extends BaseEntityAbstractController<Department, DepartmentRestTemplate>
    implements BaseEntityController<Department> {

  public DepartmentController(DepartmentRestTemplate departmentTemplate) {
    super(departmentTemplate, "department", "code,ASC");
  }
}
