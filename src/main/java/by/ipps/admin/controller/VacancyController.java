package by.ipps.admin.controller; // package by.ipps.admin.controller;

 import by.ipps.admin.controller.base.BaseEntityAbstractController;
 import by.ipps.admin.controller.base.BaseEntityController;
 import by.ipps.admin.entity.Vacancy;
 import by.ipps.admin.utils.resttemplate.VacancyRestTemplate;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;

 @RestController
 @RequestMapping("/vacancy")
 public class VacancyController extends BaseEntityAbstractController<Vacancy, VacancyRestTemplate>
        implements BaseEntityController<Vacancy> {

     protected VacancyController(VacancyRestTemplate vacancyRestTemplate) {
         super(vacancyRestTemplate, "/vacancy", "dti,desc");
     }
 }
