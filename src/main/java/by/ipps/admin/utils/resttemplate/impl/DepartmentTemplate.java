package by.ipps.admin.utils.resttemplate.impl;

import by.ipps.admin.entity.Department;
import by.ipps.admin.utils.resttemplate.DepartmentRestTemplate;
import by.ipps.admin.utils.resttemplate.base.AbstractBaseEntityRestTemplate;
import org.springframework.stereotype.Component;

@Component
public class DepartmentTemplate extends AbstractBaseEntityRestTemplate<Department>
    implements DepartmentRestTemplate {}
