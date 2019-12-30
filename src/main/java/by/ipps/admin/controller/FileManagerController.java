package by.ipps.admin.controller;

import by.ipps.admin.entity.FileManager;
import by.ipps.admin.utils.resttemplate.FileManagerRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class FileManagerController {

    private static final String ROOT_PATH = "C:\\path\\";

    private final FileManagerRestTemplate fileManagerRestTemplate;

    public FileManagerController(FileManagerRestTemplate fileManagerRestTemplate) {
        this.fileManagerRestTemplate = fileManagerRestTemplate;
    }

    @PostMapping(value = "/{type}")
    public ResponseEntity<String> saveImage(@RequestBody MultipartFile file, @PathVariable("type") String type) {
        String name = null;
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                name = file.getOriginalFilename();
                String contentType = file.getContentType().split("/")[1];
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                String path = ROOT_PATH + "loadFiles\\" + type + "\\" + calendar.get(Calendar.YEAR) + "\\" + (calendar.get(Calendar.MONTH) + 1);
                System.out.println(path);
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileManager fileManager = new FileManager(name, file.getContentType(),
                        dir.getAbsolutePath() + File.separator + name);
                File uploadedFile = new File(dir.getAbsolutePath() + File.separator + name);
                try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile))) {
                    stream.write(bytes);
                }
                fileManager = fileManagerRestTemplate.create(fileManager, "/file").getBody();
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
                        BufferedImage changedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2d = changedImage.createGraphics();
                        g2d.drawImage(image, 0, 0, null);
                        g2d.dispose();
                        ImageIO.write(changedImage, "png", new File(path + File.separator + nameSprit[0] + "-resize.png"));
                    } else {
                        BufferedImage changedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                        Graphics2D g2d = changedImage.createGraphics();
                        g2d.drawImage(image, 0, 0, null);
                        g2d.dispose();
                        ImageIO.write(changedImage, contentType, new File(path + File.separator + nameSprit[0] + "-resize." + nameSprit[1]));
                    }
                }
                return ResponseEntity.ok("{" +
                        "    \"urls\": {" +
                        "        \"default\": \"http://www.ipps.by:5454/admin-api/image/" + fileManager.getId() + "\"" +
                        "    }" +
                        "}");
            } catch (Exception e) {
                return new ResponseEntity<>("You failed to upload " + name + " => " + e.getMessage(),
                        HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("You failed to upload " + name + " because the file was empty.",
                    HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> getImageById(@PathVariable long id, HttpServletResponse response) {
        return fileManagerRestTemplate.getById(id, response);
    }

    @GetMapping(value = "/relize/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> getByIdRelize(@PathVariable long id, HttpServletResponse response) {
        return fileManagerRestTemplate.getByIdRelize(id, response);
    }
}
