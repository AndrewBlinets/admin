package by.ipps.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileManagerController {

    @PostMapping(value = "/{typeEvent}/{typefile}/{year}/{month}")
    @ResponseBody
    public String saveImage(@RequestParam("file") MultipartFile[] file) {
        return null;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getImage(HttpServletResponse response,
                                   @PathVariable long id) throws IOException {
        return null;
    }
}
