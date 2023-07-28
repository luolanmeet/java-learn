package pers.po;

import lombok.Data;
import lombok.ToString;
import pers.enums.GenderEnum;

import java.util.Date;
import java.util.List;

/**
 * @auther ken.ck
 * @date 2023/7/28 14:19
 */
@Data
@ToString
public class UserPO {

    private Long id;

    private String name;

    private int age;

    private GenderEnum gender;

    private List<String> hobby;

    private Date gmtCreate;

    private String extendProps;

}
