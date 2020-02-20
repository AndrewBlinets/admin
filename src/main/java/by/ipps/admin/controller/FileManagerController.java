package by.ipps.admin.controller;

import by.ipps.admin.entity.FileManager;
import by.ipps.admin.entity.UserAuth;
import by.ipps.admin.exception.SaveFileException;
import by.ipps.admin.utils.RestRequestToDao;
import by.ipps.admin.utils.resttemplate.FileManagerRestTemplate;
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
import java.io.*;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileManagerController {

  private static final String ROOT_PATH = "C:\\path\\";

  private final FileManagerRestTemplate fileManagerRestTemplate;
  private RestRequestToDao restRequestToDao;

  public FileManagerController(
      FileManagerRestTemplate fileManagerRestTemplate, RestRequestToDao restRequestToDao) {
    this.fileManagerRestTemplate = fileManagerRestTemplate;
    this.restRequestToDao = restRequestToDao;
  }

  @PostMapping("/file")
  public ResponseEntity<Long> saveFile(
          @RequestBody MultipartFile file) throws SaveFileException {
    return new ResponseEntity<>(getStringResponseEntity(file), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<String> saveImage(
      @RequestBody MultipartFile upload) throws SaveFileException {
    return ResponseEntity.ok(
            "{"
                    + "    \"urls\": {"
                    + "        \"default\": \"http://www.ipps.by:5454/client-api/image/"
                    + getStringResponseEntity(upload)
                    + "\""
                    + "    }"
                    + "}");
  }

  private Long getStringResponseEntity(@RequestBody MultipartFile upload) throws SaveFileException {
    String name = null;
    if (!upload.isEmpty()) {
      try {
        String type = upload.getContentType().split("/")[1];
        byte[] bytes = upload.getBytes();
        name = upload.getOriginalFilename();
        String contentType = upload.getContentType().split("/")[1];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String path =
            ROOT_PATH
                + "loadFiles\\"
                + type
                + "\\"
                + calendar.get(Calendar.YEAR)
                + "\\"
                + (calendar.get(Calendar.MONTH) + 1);
        System.out.println(path);
        File dir = new File(path);
        if (!dir.exists()) {
          dir.mkdirs();
        }
        FileManager fileManager =
            new FileManager(
                name, upload.getContentType(), dir.getAbsolutePath() + File.separator + name);
        File uploadedFile = new File(dir.getAbsolutePath() + File.separator + name);
        try (BufferedOutputStream stream =
            new BufferedOutputStream(new FileOutputStream(uploadedFile))) {
          stream.write(bytes);
        }
        fileManager = fileManagerRestTemplate.create(fileManager, "/file", getUserID()).getBody();
        BufferedImage originalImage = null;
        try (InputStream in = new ByteArrayInputStream(bytes)) {
          originalImage = ImageIO.read(in);
        }
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
