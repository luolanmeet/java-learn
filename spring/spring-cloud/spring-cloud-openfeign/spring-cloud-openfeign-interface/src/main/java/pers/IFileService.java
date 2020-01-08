package pers;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import pers.config.MultipartSupportConfig;

/**
 *
 * @author cck
 */
@FeignClient(name = "hello-service", configuration = MultipartSupportConfig.class)
public interface IFileService {

    /**
     * 普通的文件上传写法
     * @param file
     * @return
     */
    @PostMapping("uploadFile1-a")
    String uploadFile1a(@RequestParam("file") MultipartFile file);

    /**
     * 跨服务传输文件需要这样写
     * @param file
     * @return
     */
    @PostMapping(value = "uploadFile1-b", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadFile1b(@RequestPart("file") MultipartFile file);

    /**
     * 附带其他参数
     * @param file
     * @param userId
     * @return
     */
    @PostMapping(value = "uploadFile2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadFile2(@RequestPart("file") MultipartFile file, @RequestParam(name="userId", required = false) Integer userId);

    @PostMapping(value = "uploadFile3", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadFile3(@RequestPart("files") MultipartFile[] files, @RequestParam(name="userId", required = false) Integer userId);

}
