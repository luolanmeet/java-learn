package pers.service;

import pers.dto.req.QueryTraceDTO;
import pers.dto.resp.PageResult;
import pers.dto.resp.PdpTbTradeDTO;

/**
 * @auther ken.ck
 * @date 2023/8/14 16:34
 */
public interface PddService {

    /**
     * 查询销售单
     * @param queryTraceDTO
     * @return
     */
    PageResult<PdpTbTradeDTO> queryTrace(QueryTraceDTO queryTraceDTO);

}
