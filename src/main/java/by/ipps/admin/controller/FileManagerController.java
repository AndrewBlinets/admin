package by.ipps.admin.controller;

import by.ipps.admin.entity.FileManager;
import by.ipps.admin.entity.UserAuth;
import by.ipps.admin.exception.SaveFileException;
import by.ipps.admin.utils.RestRequestToDao;
import by.ipps.admin.utils.resttemplate.FileManagerRestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileManagerController {

  @Value("${upload.path}")
  private String ROOT_PATH;

  private final FileManagerRestTemplate fileManagerRestTemplate;
  private RestRequestToDao restRequestToDao;

  public FileManagerController(
      FileManagerRestTemplate fileManagerRestTemplate, RestRequestToDao restRequestToDao) {
    this.fileManagerRestTemplate = fileManagerRestTemplate;
    this.restRequestToDao = restRequestToDao;
  }

  @PostMapping("/file")
  public ResponseEntity<Long> saveFile(@RequestBody MultipartFile file)
      throws SaveFileException, IOException {
    return new ResponseEntity<>(getStringResponseEntity(file), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<String> saveImage(@RequestBody MultipartFile upload)
      throws SaveFileException, IOException {
    return ResponseEntity.ok(
        "{"
            + "    \"urls\": {"
            + "        \"default\": \"http://www.ipps.by:5454/client-api/image/"
            + getStringResponseEntity(upload)
            + "\""
            + "    }"
            + "}");
  }

  private Long getStringResponseEntity(MultipartFile file) throws SaveFileException, IOException {
    if (file != null && !file.getOriginalFilename().isEmpty()) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      String path = ROOT_PATH + file.getContentType().split("/")[1];
      File uploadDir = new File(path);
      if (!uploadDir.exists()) {
        uploadDir.mkdir();
      }
      String uuidFile = UUID.randomUUID().toString();
      String resultFilename = uuidFile + "-" + file.getOriginalFilename();
      file.transferTo(new File(path + "/" + resultFilename));
      BufferedImage originalImage = ImageIO.read(new File(path + "/" + resultFilename));
      if (originalImage != null) {
        int width = 60;
        int height = originalImage.getHeight() / (originalImage.getWidth() / 60);
        Image image = originalImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
        if (file.getContentType().split("/")[1].equals("png")) {
          BufferedImage changedImage =
              new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
          Graphics2D g2d = changedImage.createGraphics();
          g2d.drawImage(image, 0, 0, null);
          g2d.dispose();
          ImageIO.write(
              changedImage,
              "png",
              new File(path + File.separator + resultFilename.split("\\.")[0] + "-resize.png"));
        } else {
          BufferedImage changedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
          Graphics2D g2d = changedImage.createGraphics();
          g2d.drawImage(image, 0, 0, null);
          g2d.dispose();
          ImageIO.write(
              changedImage,
              file.getContentType().split("/")[1],
              new File(
                  path
                      + File.separator
                      + resultFilename.split("\\.")[0]
                      + "-resize."
                      + resultFilename.split("\\.")[1]));
        }
      }
      return Objects.requireNonNull(
              fileManagerRestTemplate
                  .create(
                      new FileManager(
                          resultFilename, file.getContentType(), path + "/" + resultFilename),
                      "/file",
                      getUserID())
                  .getBody())
          .getId();
    } else {
      throw new SaveFileException("Не удалось сохранить файл");
    }
  }

  /*private Long getStringResponseEntity(@RequestBody MultipartFile upload) throws SaveFileException {
      String name = null;
      if (!upload.isEmpty()) {
        try(InputStream in = new ByteArrayInputStream(upload.getBytes())) {
          String type = upload.getContentType().split("/")[1];
          name = upload.getOriginalFilename();
          String contentType = upload.getContentType().split("/")[1];
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(new Date());
          String path =
              ROOT_PATH + "loadFiles\\" + type + "\\" + calendar.get(Calendar.YEAR)
                      + "\\" + (calendar.get(Calendar.MONTH) + 1);
          System.out.println(path);
          File dir = new File(path);
          if (!dir.exists()) {
            dir.mkdirs();
          }
          File uploadedFile = new File(dir.getAbsolutePath() + File.separator + name);
          try (BufferedOutputStream stream =
              new BufferedOutputStream(new FileOutputStream(uploadedFile))) {
            stream.write(upload.getBytes());
          }
        FileManager fileManager = fileManagerRestTemplate.create(new FileManager(
                  name, upload.getContentType(), dir.getAbsolutePath() + File.separator + name), "/file", getUserID()).getBody();
          BufferedImage originalImage = null;
            originalImage = ImageIO.read(in);
          if (originalImage != null) {
            int width = 60;
            String[] nameSprit = name.split("\\.");
            int height = originalImage.getHeight() / (originalImage.getWidth() / 60);
            Image image = originalImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
            if (contentType.equals("png")) {
              BufferedImage changedImage =
                  new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
              Graphics2D g2d = changedImage.createGraphics();
              g2d.drawImage(image, 0, 0, null);
              g2d.dispose();
              ImageIO.write(
                  changedImage,
                  "png",
                  new File(path + File.separator + nameSprit[0] + "-resize.png"));
            } else {
              BufferedImage changedImage =
                  new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
              Graphics2D g2d = changedImage.createGraphics();
              g2d.drawImage(image, 0, 0, null);
              g2d.dispose();
              ImageIO.write(
                  changedImage,
                  contentType,
                  new File(path + File.separator + nameSprit[0] + "-resize." + nameSprit[1]));
            }
          }
          return fileManager.getId();
        } catch (Exception e) {
          e.printStackTrace();
          throw new SaveFileException("Error save");
        }
      } else {
        throw new SaveFileException("Error save");
      }
    }
  */
  @GetMapping(value = "/{id}")
  @ResponseBody
  public ResponseEntity<HttpStatus> getImageById(
      @PathVariable long id, HttpServletResponse response) {
    return fileManagerRestTemplate.getById(id, response);
  }

  @GetMapping(value = "/relize/{id}")
  @ResponseBody
  public ResponseEntity<HttpStatus> getByIdRelize(
      @PathVariable long id, HttpServletResponse response) {
    return fileManagerRestTemplate.getByIdRelize(id, response);
  }

  private Long getUserID() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = "";
    if (principal instanceof UserDetails) {
      username = ((UserDetails) principal).getUsername();
    } else {
      username = principal.toString();
    }
    UserAuth user = restRequestToDao.getUserByLogin(username);
    return user.getId();
  }
}
