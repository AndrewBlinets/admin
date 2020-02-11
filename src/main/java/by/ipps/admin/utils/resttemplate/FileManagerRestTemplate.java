package by.ipps.admin.utils.resttemplate;

import by.ipps.admin.entity.FileManager;
import by.ipps.admin.utils.resttemplate.base.BaseEntityRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
public interface FileManagerRestTemplate extends BaseEntityRestTemplate<FileManager> {

  ResponseEntity<HttpStatus> getById(long id, HttpServletResponse response);

  ResponseEntity<HttpStatus> getByIdRelize(long id, HttpServletResponse response);
}
