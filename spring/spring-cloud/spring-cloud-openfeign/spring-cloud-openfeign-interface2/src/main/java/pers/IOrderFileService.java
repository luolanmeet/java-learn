package pers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface IOrderFileService {

    @PostMapping("uploadFile")
    String uploadFile(@RequestParam("file") MultipartFile file);

    @PostMapping("uploadFile2")
    String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(name="userId", required = false) Integer userId);

}
