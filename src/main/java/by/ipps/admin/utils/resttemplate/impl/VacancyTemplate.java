package by.ipps.admin.utils.resttemplate.impl;

import by.ipps.admin.entity.Vacancy;
import by.ipps.admin.utils.resttemplate.VacancyRestTemplate;
import by.ipps.admin.utils.resttemplate.base.AbstractBaseEntityRestTemplate;
import org.springframework.stereotype.Component;

@Component
public class VacancyTemplate extends AbstractBaseEntityRestTemplate<Vacancy> implements VacancyRestTemplate {
}
