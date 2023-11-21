package pers.es;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pers.es.entity.BizPointDTO;
import pers.es.entity.HistogramsRequestDTO;
import pers.es.entity.LogRequestDTO;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther ken.ck
 * @date 2023/11/21 20:46
 */
@Service
public class LogElkServiceImpl {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 查询日志条数
     */
    public long getHistograms(HistogramsRequestDTO requestDTO) {

        // 建立一个bool查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.rangeQuery("@timestamp")
                .gte(requestDTO.getFromDate())
                .lte(requestDTO.getToDate()));
        if (StringUtils.hasText(requestDTO.getWord())) {
            boolQueryBuilder.must(QueryBuilders.queryStringQuery(requestDTO.getWord()));
        }

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();
        long count = elasticsearchRestTemplate.count(nativeSearchQuery, String.class);
        return count;
    }

    /**
     * 查询日志
     */
    public <T> List<T> querySLSLogs(LogRequestDTO logRequestDTO, Class<T> clazz) {

        // 建立一个bool查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        boolQueryBuilder.must(QueryBuilders.rangeQuery("startTime")
//                .gte(logRequestDTO.getFromDate())
//                .lte(logRequestDTO.getToDate()));
        if (StringUtils.hasText(logRequestDTO.getKword())) {
            boolQueryBuilder.must(QueryBuilders.queryStringQuery(logRequestDTO.getKword()));
        }

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();

        SearchHits<T> search = elasticsearchRestTemplate.search(nativeSearchQuery, clazz);

        List<T> result = new ArrayList<>((int) search.getTotalHits());
        search.stream().iterator().forEachRemaining(hit -> result.add(hit.getContent()));

        return result;
    }

    /**
     * 获取projectName 列表
     */
    public List<String> listProject() {
        return null;
    }

}
