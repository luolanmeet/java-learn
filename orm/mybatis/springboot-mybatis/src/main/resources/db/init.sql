
-- 发现在这里写创建库的语句，不生效，估计是配置mysql链接的时候就指定了库，因此库无法在连上mysql之后再创建
 CREATE DATABASE IF NOT EXISTS db_mybatis DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_bin;
-- USE `db_mybatis`;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_user` (
    `user_id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
    `address` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
