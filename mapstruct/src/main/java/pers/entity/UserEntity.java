package pers.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @auther ken.ck
 * @date 2023/7/28 14:22
 */
@Data
public class UserEntity {

    private Long id;

    private String userNick;

    private int age;

    private Integer gender;

    private String hobby;

    private Date gmtCreate;

    private String extendProps;

}
