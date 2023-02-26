package com.learn;

import lombok.Data;

/**
 * 示例：
 * 库存类 配置类，用来表示一组库存
 * @auther ken.ck
 * @date 2023/2/24 00:13
 */
public class InventoryConfig {

    /** 国际编码 */
    private String geoCode;

    /** 工厂编码 */
    private String plantCode;

    /** 库位编码 */
    private String locationCode;

    /** 属性编码 */
    private String attributeCode;

    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }

    public String getPlantCode() {
        return plantCode;
    }

    public void setPlantCode(String plantCode) {
        this.plantCode = plantCode;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getAttributeCode() {
        return attributeCode;
    }

    public void setAttributeCode(String attributeCode) {
        this.attributeCode = attributeCode;
    }

    @Override
    public String toString() {
        return "InventoryConfig{" +
                "geoCode='" + geoCode + '\'' +
                ", plantCode='" + plantCode + '\'' +
                ", locationCode='" + locationCode + '\'' +
                ", attributeCode='" + attributeCode + '\'' +
                '}';
    }
}
