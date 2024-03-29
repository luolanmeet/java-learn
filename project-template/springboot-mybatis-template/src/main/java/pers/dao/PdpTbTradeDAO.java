package pers.dao;

import pers.dao.databasedo.PdpTbTradeDO;
import pers.dao.databasedo.PdpTbTradeParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * This class was generated by Ali-Generator
 * @author chenken
 */
@Mapper
public interface PdpTbTradeDAO {
    /**
     * 根据WHERE条件COUNT
     * @param pdpTbTradeParam
     * @return
     *
     * @mbg.generated
     */
    long countByParam(PdpTbTradeParam pdpTbTradeParam);

    /**
     * 根据WHERE条件删除
     * @param pdpTbTradeParam
     * @return
     *
     * @mbg.generated
     */
    int deleteByParam(PdpTbTradeParam pdpTbTradeParam);

    /**
     * 根据主键删除
     * @param id
     * @return
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入单条记录
     * @param record
     * @return
     *
     * @mbg.generated
     */
    int insert(PdpTbTradeDO record);

    /**
     * 根据字段选择性插入单条记录
     * @param record
     * @return
     *
     * @mbg.generated
     */
    int insertSelective(PdpTbTradeDO record);

    /**
     * 根据WHERE条件查询，返回包含长文本字段
     * @param pdpTbTradeParam
     * @return
     *
     * @mbg.generated
     */
    List<PdpTbTradeDO> selectByParamWithBLOBs(PdpTbTradeParam pdpTbTradeParam);

    /**
     * 根据WHERE条件查询，返回不包含长文本字段
     * @param pdpTbTradeParam
     * @return
     *
     * @mbg.generated
     */
    List<PdpTbTradeDO> selectByParam(PdpTbTradeParam pdpTbTradeParam);

    /**
     * 根据主键查询
     * @param id
     * @return
     *
     * @mbg.generated
     */
    PdpTbTradeDO selectByPrimaryKey(Long id);

    /**
     * 根据WHERE条件选择性更新
     * @param record
     * @param pdpTbTradeParam
     * @return
     *
     * @mbg.generated
     */
    int updateByParamSelective(@Param("record") PdpTbTradeDO record, @Param("pdpTbTradeParam") PdpTbTradeParam pdpTbTradeParam);

    /**
     * 根据WHERE条件更新，更新长文本字段
     * @param record
     * @param pdpTbTradeParam
     * @return
     *
     * @mbg.generated
     */
    int updateByParamWithBLOBs(@Param("record") PdpTbTradeDO record, @Param("pdpTbTradeParam") PdpTbTradeParam pdpTbTradeParam);

    /**
     * 根据WHERE条件更新，不更新长文本字段
     * @param record
     * @param pdpTbTradeParam
     * @return
     *
     * @mbg.generated
     */
    int updateByParam(@Param("record") PdpTbTradeDO record, @Param("pdpTbTradeParam") PdpTbTradeParam pdpTbTradeParam);

    /**
     * 根据主键选择性更新
     * @param record
     * @return
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PdpTbTradeDO record);

    /**
     * 根据主键更新，更新长文本字段
     * @param record
     * @return
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(PdpTbTradeDO record);

    /**
     * 根据主键更新，不更新长文本字段
     * @param record
     * @return
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PdpTbTradeDO record);

    /**
     * 批量插入
     * @param records
     * @return
     *
     * @mbg.generated
     */
    int batchInsert(List<PdpTbTradeDO> records);
}