package pers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.es.LogElkServiceImpl;
import pers.es.entity.BizPointDTO;
import pers.es.entity.LogRequestDTO;

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
    public List<BizPointDTO> search(String msg) {

        // http://localhost:7001/search?msg=753e78fd97194545815bc4822fd677ddsblkh
        // http://localhost:7001/search?msg=UnifiedCommon

        LogRequestDTO logRequestDTO = new LogRequestDTO();
        logRequestDTO.setFromDate("2023-11-14 11:30:31.278");
        logRequestDTO.setToDate("2023-11-14 21:30:31.278");
        if (StringUtils.hasText(msg)) {
            logRequestDTO.setKword(msg);
        }

        List<BizPointDTO> bizPointDTOS = logElkServiceImpl.querySLSLogs(logRequestDTO, BizPointDTO.class);
        return bizPointDTOS;
    }

}
