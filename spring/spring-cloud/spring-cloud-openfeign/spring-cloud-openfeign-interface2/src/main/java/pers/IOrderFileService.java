package pers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface IOrderFileService {

    @PostMapping("uploadFile1")
    String uploadFile1(@RequestParam("file") MultipartFile file);

    @PostMapping("uploadFile2")
    String uploadFile2(@RequestParam("file") MultipartFile file, @RequestParam(name="userId", required = false) Integer userId);

    @PostMapping("uploadFile3")
    String uploadFile3(@RequestParam("files") MultipartFile[] files, @RequestParam(name="userId", required = false) Integer userId);

}
