package by.ipps.admin.utils.resttemplate.impl;

import by.ipps.admin.entity.News;
import by.ipps.admin.utils.resttemplate.NewsRestTemplate;
import by.ipps.admin.utils.resttemplate.base.AbstractBaseEntityRestTemplate;
import org.springframework.stereotype.Component;

@Component
public class NewsTemplate extends AbstractBaseEntityRestTemplate<News> implements
    NewsRestTemplate {

}
