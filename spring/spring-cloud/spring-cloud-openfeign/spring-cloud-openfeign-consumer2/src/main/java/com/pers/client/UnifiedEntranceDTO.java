package com.pers.client;

import java.io.Serializable;

/**
 * @auther ken.ck
 * @date 2022/4/18 17:03
 */
public class UnifiedEntranceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 应用编码 */
    private String appCode;
    /** 接口模型方编码 */
    private String apiModuleCode;
    /** 请求报文 */
    private String body;
    /**请求报文格式*/
    private String format;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getApiModuleCode() {
        return apiModuleCode;
    }

    public void setApiModuleCode(String apiModuleCode) {
        this.apiModuleCode = apiModuleCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
