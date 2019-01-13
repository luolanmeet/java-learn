package com;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * 
 * @author cck
 */
public class ESDemo {
    
    // Java 客户端与ES通信使用 9300 端口
    final static int PORT = 9300; 
    final static String HOST = "localhost"; 
    
    private Logger logger = LogManager.getLogger(ESDemo.class);
    
    private TransportClient transportClient;
    
    /**
     * 建立链接
     * @throws UnknownHostException 
     */
    @SuppressWarnings("resource")
    public void connect() throws UnknownHostException {
            
        // 声明通信地址
        TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(HOST), PORT);
        
        // 创建一个客户端
        transportClient = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(transportAddress);
        
        logger.info("es connet info {}", transportClient.toString());
    }
    
    /**
     * 建立索引库（数据库） 
     * 将一个文档索引到一个索引的类型下..  插入一条数据到每个数据库的某张表中
     * @throws IOException 
     */
    public void index() throws IOException {
        
        // 索引数据的准备也可以用 map 或是 fastjson 的JsonObject
        
        // 要索引的json数据
        XContentBuilder XCBuilder = XContentFactory.jsonBuilder()
            .startObject()
                .field("user", "Cck")
                .field("postDate", new Date())
                .field("msg", "trying out Elasticsearch")
            .endObject();
        
        // 声明一个名为 twitter 的索引，并声明了这个索引下边名为 tweet 的类型
        // 索引一个文档（插入一条记录）, 文档id 为 1 (可以为空,不指定会自动生成，像 5Fc2n2MB3vmMOZ9Wb1cq 这样)
        IndexResponse response 
            = transportClient.prepareIndex("twitter", "tweet", "1")
                             .setSource(XCBuilder)
                             .get();
        
        logger.info("索引名：{}, 类型：{}, 文档id：{}, 状态：{}", 
                    response.getIndex(), 
                    response.getType(),
                    response.getId(),
                    response.status());
    }
    
    /**
     * 查找
     */
    public void search() {
        
        // 构造查找的请求
        // 查找 索引为twitter下 类型为tweet id为1 的数据
        GetRequestBuilder search = transportClient.prepareGet("twitter", "tweet", "1");
        // 查找
        GetResponse response = search.get();
        logger.info("查找到的数据为： {}", response.getSourceAsString());
    }
    
    /**
     * 更新
     */
    public void update() throws IOException {
        
        // 构造更新的数据
        // 对于文档中有的字段进行更新，对于没有的字段进行增加
        XContentBuilder XCBuilder = XContentFactory.jsonBuilder()
                .startObject()
                    .field("user", "cck")
                    .field("gender", "male")
                .endObject();
        
        UpdateRequestBuilder update = transportClient.prepareUpdate("twitter", "tweet", "1");
        update.setDoc(XCBuilder);
        UpdateResponse response = update.get();
        logger.info("更新的结果为： {}", response.getResult());
    }
    
    /**
     * 更新插入
     * 存在就更新，不存在就插入
     */
    public void upsert() throws IOException, InterruptedException, ExecutionException {
        
        IndexRequest indexRequest = new IndexRequest("twitter", "tweet", "1")  
                .source(XContentFactory.jsonBuilder()  
                    .startObject()
                        .field("gender", "male")
                    .endObject());
        
        UpdateRequest updateRequest = new UpdateRequest("twitter", "tweet", "1")  
                .doc(XContentFactory.jsonBuilder()
                    .startObject()
                        .field("user", "ck")
                        .field("gender", "male")
                    .endObject())
                .upsert(indexRequest);  // 划重点
        
        // UpdateRequestBuilder 下也有  update.setDocAsUpsert()的方法
        
        transportClient.update(updateRequest).get();  
    }
    
    /**
     * 删除
     */
    public void delete() {
        
        // 构造删除的的请求并执行
        DeleteResponse response = transportClient
                                    .prepareDelete("twitter", "tweet", "1")
                                    .get();
        logger.info("删除的结果为： {}", response.getResult());
    }
    
    public void closeConnect() {
        
        if (transportClient != null) {
            transportClient.close();
        }
    }
    
    public static void main(String[] args) throws Exception {
        
        ESDemo esDemo = new ESDemo();
        esDemo.connect();
        
        esDemo.index();
        
        esDemo.search();
        
        esDemo.update();
        
        esDemo.search();
        
        esDemo.upsert();
        
        esDemo.search();
        
        esDemo.delete();
        
        esDemo.closeConnect();
    }
    
}
