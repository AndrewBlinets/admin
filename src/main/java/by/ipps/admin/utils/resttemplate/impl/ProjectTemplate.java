package by.ipps.admin.utils.resttemplate.impl;

import by.ipps.admin.entity.Project;
import by.ipps.admin.utils.resttemplate.ProjectRestTemplate;
import by.ipps.admin.utils.resttemplate.base.AbstractBaseEntityRestTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProjectTemplate extends AbstractBaseEntityRestTemplate<Project>
    implements ProjectRestTemplate {}
