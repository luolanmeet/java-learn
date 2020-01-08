package pers.service;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pers.IFileService;

import java.io.File;
import java.io.IOException;

@RestController
public class FileService implements IFileService {

    @Override
    public String uploadFile1a(MultipartFile file) {

        if (file == null) {
            System.out.println("\r\n uploadFile. file is null \r\n");
            return "false";
        }

        System.out.println("\r\n uploadFile. got file. " + file.getOriginalFilename() + "\r\n");
        return "success";
    }

    @Override
    public String uploadFile1b(MultipartFile file) {

        if (file == null) {
            System.out.println("\r\n uploadFile2. file is null \r\n");
            return "false";
        }

        System.out.println("\r\n uploadFile2. got file. " + file.getOriginalFilename() + "\r\n");
        try {
            file.transferTo(new File("D:\\" +  "2-" + file.getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @Override
    public String uploadFile2(MultipartFile file, Integer userId) {

        if (file == null) {
            System.out.println("\r\n uploadFile2. file is null, userId is + "+ userId + " \r\n");
            return "false";
        }

        System.out.println("\r\n uploadFile3. got file. " + file.getOriginalFilename() + " userId " + userId + "\r\n");
        try {
            file.transferTo(new File("D:\\" +  "3-" + file.getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

}
