package pers.enums;

/**
 * @auther ken.ck
 * @date 2023/7/28 14:58
 */
public enum GenderEnum {

    MALE(1, "男"),
    FEMALE(0, "女"),
    ;

    private Integer code;
    private String desc;

    GenderEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
