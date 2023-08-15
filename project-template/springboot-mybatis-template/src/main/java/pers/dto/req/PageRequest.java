package pers.dto.req;

import lombok.Data;

/**
 * @auther ken.ck
 * @date 2023/6/20 15:30
 */
@Data
public class PageRequest {

    private Integer pageSize;

    private Integer currentPage;

    private Long modifiedStart;

    private Long modifiedEnd;

    private String status;

}
