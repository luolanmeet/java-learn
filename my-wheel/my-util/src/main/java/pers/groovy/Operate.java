package pers.groovy;

/**
 * 操作
 * @auther ken.ck
 * @date 2021/4/29 20:41
 */
public class Operate {

    private String operateType;

    private String operateVal;

    public Operate(String operateType, String operateVal) {
        this.operateType = operateType;
        this.operateVal = operateVal;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getOperateVal() {
        return operateVal;
    }

    public void setOperateVal(String operateVal) {
        this.operateVal = operateVal;
    }
}
