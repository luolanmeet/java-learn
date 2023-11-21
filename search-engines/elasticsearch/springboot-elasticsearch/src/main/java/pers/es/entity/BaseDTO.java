package pers.es.entity;

import lombok.Getter;

import java.io.Serializable;

/**
 * 基础DTO
 */
@Getter
public class BaseDTO implements Serializable {

    /** 包路径类名*/
    private String className = this.getClass().getName();

}
