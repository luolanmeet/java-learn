package pers.errorcode.result;

import java.util.List;

/**
 * @auther ken.ck
 * @date 2023/3/12 13:24
 */
public class PageResult<E> extends Result<List<E>> {

    private final Integer totalCount;
    private final Integer currentPage;
    private final Integer pageSize;

    protected PageResult(boolean success, String errorCode, String errorMessage, List<E> data, Integer currentPage, Integer pageSize, Integer count) {
        super(success, errorCode, errorMessage, data);
        this.currentPage = currentPage;
        this.totalCount = count;
        this.pageSize = pageSize;
    }

    public static <E> PageResult<E> ofSuccessPage(Integer currentPage, Integer pageSize, Integer count, List<E> data) {
        return new PageResult(true, null, null, data, currentPage, pageSize, count);
    }

    public static <E> PageResult<E> ofFailPage(String errorCode, String errorMessage) {
        return new PageResult(false, errorCode, errorMessage, null, null, null, null);
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

}
