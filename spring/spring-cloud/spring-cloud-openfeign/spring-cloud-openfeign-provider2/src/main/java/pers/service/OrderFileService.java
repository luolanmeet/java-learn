package pers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pers.IFileService;
import pers.IOrderFileService;

import java.io.File;
import java.io.IOException;

@RestController
public class OrderFileService implements IOrderFileService {

    @Autowired
    private IFileService fileService;

    @Override
    public String uploadFile(MultipartFile file) {

        if (file == null) {
            System.out.println("\r\n uploadFile. file is null \r\n");
            return "false";
        }

        // 调用接口，传递MultipartFile需要特殊处理
        // 这样是会失败的
        String resOne = fileService.uploadFile(file);
        System.out.println(resOne);
        // 这样才可以
        String resTwo = fileService.uploadFile2(file);
        System.out.println(resTwo);

        return "result one:" + resOne + " result two:" + resTwo;
    }

    @Override
    public String uploadFile(MultipartFile file, Integer userId) {

        if (file == null) {
            System.out.println("\r\n uploadFile2. file is null, userId is + "+ userId + " \r\n");
            return "false";
        }

        System.out.println("\r\n uploadFile. got file. " + file.getOriginalFilename() + " userId " + userId + "\r\n");
        return fileService.uploadFile3(file, userId);
    }

}
