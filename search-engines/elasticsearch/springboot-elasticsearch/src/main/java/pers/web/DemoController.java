package pers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.es.LogElkServiceImpl;
import pers.es.entity.BizPointDTO;
import pers.es.entity.LogRequestDTO;
import pers.es.entity.PageResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @auther ken.ck
 * @date 2023/11/21 19:21
 */
@RestController
public class DemoController {

    @Autowired
    private LogElkServiceImpl logElkServiceImpl;

    @RequestMapping("/search")
    public PageResult<BizPointDTO> search(String msg, Integer page, Integer size) throws ParseException {

        // http://localhost:7001/search?msg=753e78fd97194545815bc4822fd677ddsblkh
        // http://localhost:7001/search?msg=UnifiedCommon

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        logRequestDTO.setFrom(sdf.parse("2023-11-14 11:30:31"));
        logRequestDTO.setTo(sdf.parse("2028-11-23 21:30:31"));

        if (StringUtils.hasText(msg)) {
            logRequestDTO.setKword(msg);
        }
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        PageResult<BizPointDTO> result = logElkServiceImpl.querySLSLogs(logRequestDTO, page, size, BizPointDTO.class);
        return result;
    }

}
