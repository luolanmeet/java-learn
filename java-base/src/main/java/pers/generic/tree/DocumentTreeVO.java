package pers.generic.tree;

import java.util.List;

/**
 * 文档树 VO
 * @auther ken.ck
 * @date 2022/12/17 19:20
 */
public class DocumentTreeVO extends TreeVO {

    /**
     * 接口id
     */
    private Long appApiId;

    /**
     * 接口名称
     */
    private Long appApiName;

    /**
     * 版本
     */
    private List<String> version;

    public Long getAppApiId() {
        return appApiId;
    }

    public void setAppApiId(Long appApiId) {
        this.appApiId = appApiId;
    }

    public Long getAppApiName() {
        return appApiName;
    }

    public void setAppApiName(Long appApiName) {
        this.appApiName = appApiName;
    }

    public List<String> getVersion() {
        return version;
    }

    public void setVersion(List<String> version) {
        this.version = version;
    }
}
