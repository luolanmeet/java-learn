package pers.es.entity;

import lombok.Data;

/**
 * @auther ken.ck
 * @date 2023/11/22 15:22
 */
@Data
public class Result<T> {

    private T data;

}
