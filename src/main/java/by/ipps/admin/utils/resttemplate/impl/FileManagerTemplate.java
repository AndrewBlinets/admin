package by.ipps.admin.utils.resttemplate.impl;

import by.ipps.admin.entity.FileManager;
import by.ipps.admin.utils.resttemplate.FileManagerRestTemplate;
import by.ipps.admin.utils.resttemplate.base.AbstractBaseEntityRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@Component
public class FileManagerTemplate extends AbstractBaseEntityRestTemplate<FileManager>
    implements FileManagerRestTemplate {
  @Override
  public ResponseEntity<HttpStatus> getById(long id, HttpServletResponse response) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_SERVER + "file/" + id);
    try {
      ResponseEntity<FileManager> result =
          restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, FileManager.class);
      response.setContentType(Objects.requireNonNull(result.getBody()).getFileMine());
      response.setHeader(
          "Content-Disposition", "attachment; filename=" + result.getBody().getFileName());
      byte[] array =
          Files.readAllBytes(
              Paths.get(
                  result.getBody().getPath() + File.separator + result.getBody().getFileName()));
      response.getOutputStream().write(array);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (org.springframework.web.client.HttpClientErrorException exception) {
      //            log.info("getImageByID " + id);
      //            log.info(URL_SERVER);
      //            log.error(exception.getStatusCode() + " " + exception.getStatusText());
      return new ResponseEntity<>(HttpStatus.valueOf(exception.getStatusCode().value()));
    } catch (Exception e) {
      //            log.info("getImageByID");
      //            log.info(URL_SERVER);
      //            log.error(e.getMessage());
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ResponseEntity<HttpStatus> getByIdRelize(long id, HttpServletResponse response) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_SERVER + "file/" + id);
    try {
      ResponseEntity<FileManager> result =
          restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, FileManager.class);
      response.setContentType(Objects.requireNonNull(result.getBody()).getFileMine());
      response.setHeader(
          "Content-Disposition", "attachment; filename=" + result.getBody().getFileName());
      String nameFile =
          result.getBody().getFileName().split("\\.")[0]
              + "-resize."
              + result.getBody().getFileName().split("\\.")[1];
      byte[] array =
          Files.readAllBytes(Paths.get(result.getBody().getPath() + File.separator + nameFile));
      response.getOutputStream().write(array);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (org.springframework.web.client.HttpClientErrorException exception) {
      //            log.info("getImageByID " + id);
      //            log.info(URL_SERVER);
      //            log.error(exception.getStatusCode() + " " + exception.getStatusText());
      return new ResponseEntity<>(HttpStatus.valueOf(exception.getStatusCode().value()));
    } catch (Exception e) {
      //            log.info("getImageByID");
      //            log.info(URL_SERVER);
      //            log.error(e.getMessage());
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
