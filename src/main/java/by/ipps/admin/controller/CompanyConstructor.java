package by.ipps.admin.controller;

import by.ipps.admin.entity.Company;
import by.ipps.admin.utils.resttemplate.impl.CompanyRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@CrossOrigin
public class CompanyConstructor {

  private CompanyRestTemplate restTemplate;

  public CompanyConstructor(CompanyRestTemplate companyService) {
    this.restTemplate = companyService;
  }

  @GetMapping
  @ResponseBody
  public ResponseEntity<Company> getInformationAboutCompany() {
    return restTemplate.getActualInfo();
  }

  @PostMapping
  public ResponseEntity<Company> setInformationAboutCompany(@RequestBody Company company) {
    return restTemplate.setActualInfo(company);
  }
}
