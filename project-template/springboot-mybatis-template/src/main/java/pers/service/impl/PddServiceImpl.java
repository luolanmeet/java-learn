package pers.service.impl;

import com.alibaba.fastjson2.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.dao.PdpTbTradeDAO;
import pers.dto.resp.PageResult;
import pers.dto.resp.PdpTbTradeDTO;
import pers.service.PddService;
import pers.dto.req.QueryTraceDTO;
import pers.dao.databasedo.PdpTbTradeParam;
import pers.dao.databasedo.PdpTbTradeDO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther ken.ck
 * @date 2023/8/14 16:40
 */
@Service
public class PddServiceImpl implements PddService {

    private static final String REGEX = ",";

    private static final Logger logger = LoggerFactory.getLogger(PddServiceImpl.class);

    @Autowired
    private PdpTbTradeDAO pdpTbTradeDAO;

    @Override
    public PageResult<PdpTbTradeDTO> queryTrace(QueryTraceDTO queryDTO) {

        // https://open.pinduoduo.com/application/document/api?id=pdd.order.information.get

        logger.info("{}", queryDTO);

        PdpTbTradeParam param = new PdpTbTradeParam();
        param.setPage(true);
        param.setPageSize(queryDTO.getPageSize());
        param.setPageStart(queryDTO.getCurrentPage());
        param.appendOrderByClause(PdpTbTradeParam.OrderCondition.UPDATEDAT, PdpTbTradeParam.SortType.DESC);
        PdpTbTradeParam.Criteria criteria = param.createCriteria();
        if (queryDTO.getModifiedStart() != null) {
            criteria.andUpdatedAtGreaterThanOrEqualTo(new Date(queryDTO.getModifiedStart()));
        }
        if (queryDTO.getModifiedEnd() != null) {
            criteria.andUpdatedAtLessThanOrEqualTo(new Date(queryDTO.getModifiedEnd()));
        }
        if (StringUtils.isNotBlank(queryDTO.getStatus())) {
            List<Integer> status = Arrays.stream(queryDTO.getStatus().split(REGEX))
                    .map(Integer::valueOf).collect(Collectors.toList());
            criteria.andOrderStatusIn(status);
        }

        long total = pdpTbTradeDAO.countByParam(param);

        PageResult<PdpTbTradeDTO> result = new PageResult<>();
        List<PdpTbTradeDTO> data = new ArrayList<>();
        result.setPageSize(queryDTO.getPageSize());
        result.setData(data);
        result.setTotal(total);
        if (total <= 0) {
            return result;
        }

        List<PdpTbTradeDO> jdpTbTradeDOS = pdpTbTradeDAO.selectByParamWithBLOBs(param);
        for (PdpTbTradeDO pdpTbTradeDO : jdpTbTradeDOS) {
            PdpTbTradeDTO pdpTbTradeDTO = new PdpTbTradeDTO();
            BeanUtils.copyProperties(pdpTbTradeDO, pdpTbTradeDTO);
            pdpTbTradeDTO.setPdpResponse(JSON.parse(pdpTbTradeDO.getPdpResponse()));
            data.add(pdpTbTradeDTO);
        }
        return result;
    }

}
