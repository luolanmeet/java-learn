package pers.dao.databasedo;

import java.util.Date;

/**
 * Database Table Remarks:
 *   pdd销售订单表
 *
 * @author chenken
 */
public class PdpTbTradeDO {
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
    private String pdpResponse;

    /**
     *
     * @return the value of pdp_tb_trade.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id the value for pdp_tb_trade.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return the value of pdp_tb_trade.order_sn
     *
     * @mbg.generated
     */
    public String getOrderSn() {
        return orderSn;
    }

    /**
     *
     * @param orderSn the value for pdp_tb_trade.order_sn
     *
     * @mbg.generated
     */
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    /**
     *
     * @return the value of pdp_tb_trade.group_status
     *
     * @mbg.generated
     */
    public Integer getGroupStatus() {
        return groupStatus;
    }

    /**
     *
     * @param groupStatus the value for pdp_tb_trade.group_status
     *
     * @mbg.generated
     */
    public void setGroupStatus(Integer groupStatus) {
        this.groupStatus = groupStatus;
    }

    /**
     *
     * @return the value of pdp_tb_trade.order_status
     *
     * @mbg.generated
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     *
     * @param orderStatus the value for pdp_tb_trade.order_status
     *
     * @mbg.generated
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     *
     * @return the value of pdp_tb_trade.refund_status
     *
     * @mbg.generated
     */
    public Integer getRefundStatus() {
        return refundStatus;
    }

    /**
     *
     * @param refundStatus the value for pdp_tb_trade.refund_status
     *
     * @mbg.generated
     */
    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    /**
     *
     * @return the value of pdp_tb_trade.confirm_status
     *
     * @mbg.generated
     */
    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    /**
     *
     * @param confirmStatus the value for pdp_tb_trade.confirm_status
     *
     * @mbg.generated
     */
    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    /**
     *
     * @return the value of pdp_tb_trade.trade_type
     *
     * @mbg.generated
     */
    public Integer getTradeType() {
        return tradeType;
    }

    /**
     *
     * @param tradeType the value for pdp_tb_trade.trade_type
     *
     * @mbg.generated
     */
    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    /**
     *
     * @return the value of pdp_tb_trade.mall_id
     *
     * @mbg.generated
     */
    public Integer getMallId() {
        return mallId;
    }

    /**
     *
     * @param mallId the value for pdp_tb_trade.mall_id
     *
     * @mbg.generated
     */
    public void setMallId(Integer mallId) {
        this.mallId = mallId;
    }

    /**
     *
     * @return the value of pdp_tb_trade.confirm_time
     *
     * @mbg.generated
     */
    public String getConfirmTime() {
        return confirmTime;
    }

    /**
     *
     * @param confirmTime the value for pdp_tb_trade.confirm_time
     *
     * @mbg.generated
     */
    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    /**
     *
     * @return the value of pdp_tb_trade.updated_at
     *
     * @mbg.generated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt the value for pdp_tb_trade.updated_at
     *
     * @mbg.generated
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return the value of pdp_tb_trade.pdp_version
     *
     * @mbg.generated
     */
    public Integer getPdpVersion() {
        return pdpVersion;
    }

    /**
     *
     * @param pdpVersion the value for pdp_tb_trade.pdp_version
     *
     * @mbg.generated
     */
    public void setPdpVersion(Integer pdpVersion) {
        this.pdpVersion = pdpVersion;
    }

    /**
     *
     * @return the value of pdp_tb_trade.pdp_created
     *
     * @mbg.generated
     */
    public Date getPdpCreated() {
        return pdpCreated;
    }

    /**
     *
     * @param pdpCreated the value for pdp_tb_trade.pdp_created
     *
     * @mbg.generated
     */
    public void setPdpCreated(Date pdpCreated) {
        this.pdpCreated = pdpCreated;
    }

    /**
     *
     * @return the value of pdp_tb_trade.pdp_modified
     *
     * @mbg.generated
     */
    public Date getPdpModified() {
        return pdpModified;
    }

    /**
     *
     * @param pdpModified the value for pdp_tb_trade.pdp_modified
     *
     * @mbg.generated
     */
    public void setPdpModified(Date pdpModified) {
        this.pdpModified = pdpModified;
    }

    /**
     *
     * @return the value of pdp_tb_trade.pdp_response
     *
     * @mbg.generated
     */
    public String getPdpResponse() {
        return pdpResponse;
    }

    /**
     *
     * @param pdpResponse the value for pdp_tb_trade.pdp_response
     *
     * @mbg.generated
     */
    public void setPdpResponse(String pdpResponse) {
        this.pdpResponse = pdpResponse;
    }

    /**
     * @return
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderSn=").append(orderSn);
        sb.append(", groupStatus=").append(groupStatus);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", refundStatus=").append(refundStatus);
        sb.append(", confirmStatus=").append(confirmStatus);
        sb.append(", tradeType=").append(tradeType);
        sb.append(", mallId=").append(mallId);
        sb.append(", confirmTime=").append(confirmTime);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", pdpVersion=").append(pdpVersion);
        sb.append(", pdpCreated=").append(pdpCreated);
        sb.append(", pdpModified=").append(pdpModified);
        sb.append(", pdpResponse=").append(pdpResponse);
        sb.append("]");
        return sb.toString();
    }
}