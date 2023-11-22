package pers.es.entity;

import lombok.Data;

import java.util.List;

/**
 * @auther ken.ck
 * @date 2023/11/22 15:23
 */
@Data
public class PageResult<E> extends Result<List<E>> {

    private Long totalCount;

    private Integer currentPage;

    private Integer pageSize;

}
