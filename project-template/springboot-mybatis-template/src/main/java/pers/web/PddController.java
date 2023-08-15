package pers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pers.service.PddService;
import pers.dto.resp.PdpTbTradeDTO;
import pers.dto.req.QueryTraceDTO;
import pers.dto.resp.PageResult;


/**
 * @auther ken.ck
 * @date 2023/8/14 16:31
 */
@RestController
@RequestMapping("/api")
public class PddController {

    @Autowired
    private PddService pddService;

    @RequestMapping(path ="/queryTrace" ,method = {RequestMethod.POST})
    public PageResult<PdpTbTradeDTO> queryTrace(@RequestBody QueryTraceDTO queryTrace) {
        return pddService.queryTrace(queryTrace);
    }

}
