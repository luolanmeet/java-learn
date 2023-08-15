package pers.dto.resp;

import lombok.Data;

import java.util.Date;

/**
 * Database Table Remarks:
 *   pdd销售订单表
 *
 * @author chenken
 */
@Data
public class PdpTbTradeDTO {
    /**
     * Database Column Remarks:
     *   自增主键
     *
     *
     * @mbg.generated
     */
    private Long id;

    /**
     * Database Column Remarks:
     *   订单号
     *
     *
     * @mbg.generated
     */
    private String orderSn;

    /**
     * Database Column Remarks:
     *   成团状态
     *
     *
     * @mbg.generated
     */
    private Integer groupStatus;

    /**
     * Database Column Remarks:
     *   订单状态
     *
     *
     * @mbg.generated
     */
    private Integer orderStatus;

    /**
     * Database Column Remarks:
     *   退款状态
     *
     *
     * @mbg.generated
     */
    private Integer refundStatus;

    /**
     * Database Column Remarks:
     *   成交状态
     *
     *
     * @mbg.generated
     */
    private Integer confirmStatus;

    /**
     * Database Column Remarks:
     *   交易类型
     *
     *
     * @mbg.generated
     */
    private Integer tradeType;

    /**
     * Database Column Remarks:
     *   商家编号
     *
     *
     * @mbg.generated
     */
    private Integer mallId;

    /**
     * Database Column Remarks:
     *   订单确认时间
     *
     *
     * @mbg.generated
     */
    private String confirmTime;

    /**
     * Database Column Remarks:
     *   订单更新时间
     *
     *
     * @mbg.generated
     */
    private Date updatedAt;

    /**
     * Database Column Remarks:
     *   内容版本号
     *
     *
     * @mbg.generated
     */
    private Integer pdpVersion;

    /**
     * Database Column Remarks:
     *   记录创建时间
     *
     *
     * @mbg.generated
     */
    private Date pdpCreated;

    /**
     * Database Column Remarks:
     *   记录更新时间
     *
     *
     * @mbg.generated
     */
    private Date pdpModified;

    /**
     * Database Column Remarks:
     *   API 返回的整个JSON 字符串，格式和 API 保持一致(参考pdd.order.information.get)
     *
     *
     * @mbg.generated
     */
    private Object pdpResponse;

}