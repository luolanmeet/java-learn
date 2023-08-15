启动参数
```
-Dspring.profiles.active=daily
```

sql
```
CREATE DATABASE `sys_info`;
```
```
CREATE TABLE `pdp_tb_trade` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `order_sn` varchar(32) DEFAULT NULL COMMENT '订单号',
  `group_status` int DEFAULT NULL COMMENT '成团状态',
  `order_status` int DEFAULT NULL COMMENT '订单状态',
  `refund_status` int DEFAULT NULL COMMENT '退款状态',
  `confirm_status` int DEFAULT NULL COMMENT '成交状态',
  `trade_type` int DEFAULT NULL COMMENT '交易类型',
  `mall_id` int DEFAULT NULL COMMENT '商家编号',
  `confirm_time` varchar(100) DEFAULT NULL COMMENT '订单确认时间',
  `updated_at` datetime DEFAULT NULL COMMENT '订单更新时间',
  `pdp_version` int DEFAULT NULL COMMENT '内容版本号',
  `pdp_created` datetime DEFAULT NULL COMMENT '记录创建时间',
  `pdp_modified` datetime DEFAULT NULL COMMENT '记录更新时间',
  `pdp_response` mediumtext COMMENT 'API 返回的整个JSON 字符串，格式和 API 保持一致(参考pdd.order.information.get)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='pdd销售订单表';
```


测试
``` 
curl --location 'http://127.0.0.1:7777/api/queryTrace' \
--header 'Content-Type: application/json' \
--header 'TRACE_ID: a73beb03848a459bb2cb7ef0dc142689rvyah' \
--data '{
"pageSize": 100,
"currentPage": 0,
"modifiedStart": 1687161538000,
"modifiedEnd": 1687247938000
}'
```