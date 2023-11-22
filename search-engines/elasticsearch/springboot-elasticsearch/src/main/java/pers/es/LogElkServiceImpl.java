package pers.es;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pers.es.entity.LogRequestDTO;
import pers.es.entity.PageResult;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @auther ken.ck
 * @date 2023/11/21 20:46
 */
@Service
public class LogElkServiceImpl {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 查询日志
     */
    public <T> PageResult<T> querySLSLogs(LogRequestDTO logRequestDTO, int currentPage, int pageSize, Class<T> clazz) {

        SimpleDateFormat sdfutc = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdfutc.setTimeZone(TimeZone.getTimeZone("UTC"));

        // 建立一个bool查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.rangeQuery("@timestamp")
                .gte(sdfutc.format(logRequestDTO.getFrom()))
                .lte(sdfutc.format(logRequestDTO.getTo())));
        if (StringUtils.hasText(logRequestDTO.getKword())) {
            boolQueryBuilder.must(QueryBuilders.queryStringQuery(logRequestDTO.getKword()));
        }

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withSorts(Arrays.asList(SortBuilders.fieldSort("@timestamp").order(SortOrder.DESC)))
                .withPageable(PageRequest.of(currentPage, pageSize))
                .build();

        // 查询总数
        long total = elasticsearchRestTemplate.count(nativeSearchQuery, clazz);
        PageResult<T> result = new PageResult<>();
        result.setCurrentPage(currentPage);
        result.setPageSize(pageSize);
        result.setTotalCount(total);
        if (total <= 0) {
            return result;
        }

        // 查询数据
        SearchHits<T> search = elasticsearchRestTemplate.search(nativeSearchQuery, clazz);
        List<T> queryResult = new ArrayList<>((int) search.getTotalHits());
        search.stream().iterator().forEachRemaining(hit -> queryResult.add(hit.getContent()));

        result.setData(queryResult);
        return result;
    }

}
