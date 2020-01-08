package pers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pers.IFileService;
import pers.IOrderFileService;

@RestController
public class OrderFileService implements IOrderFileService {

    @Autowired
    private IFileService fileService;

    @Override
    public String uploadFile1(MultipartFile file) {

        if (file == null) {
            System.out.println("\r\n uploadFile. file is null \r\n");
            return "false";
        }

        // 调用接口，传递MultipartFile需要使用@RequestParam
        // 这样是会失败的
        String resOne = fileService.uploadFile1a(file);
        System.out.println(resOne);
        // 这样才可以
        String resTwo = fileService.uploadFile1b(file);
        System.out.println(resTwo);

        return "result one:" + resOne + " result two:" + resTwo;
    }

    @Override
    public String uploadFile2(MultipartFile file, Integer userId) {

        if (file == null) {
            System.out.println("\r\n uploadFile2. file is null, userId is + "+ userId + " \r\n");
            return "false";
        }

        System.out.println("\r\n uploadFile. got file. " + file.getOriginalFilename() + " userId " + userId + "\r\n");
        return fileService.uploadFile2(file, userId);
    }

    @Override
    public String uploadFile3(MultipartFile[] files, Integer userId) {

        if (files == null) {
            System.out.println("\r\n uploadFile. file is null, userId is + "+ userId + " \r\n");
            return "false";
        }

        for (MultipartFile file : files) {
            System.out.println(file.getOriginalFilename());
        }

//        fileService.uploadFile3(files, userId);

        return "success";
    }

}
