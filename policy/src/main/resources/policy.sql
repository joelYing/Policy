-- DROP DATABASE 360kan;
-- CREATE DATABASE 360kan DEFAULT CHARACTER SET utf8;

-- USE 360kan;


# CREATE TABLE `policies` (
#   `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
#   `source_id` int(10) NOT NULL COMMENT '信源对应ID',
#   `source_name` varchar(128) NOT NULL DEFAULT '' COMMENT '信源名称',
#   `country` tinyint(1) NOT NULL DEFAULT '0' COMMENT '中央级',
#   `province` varchar(64) NOT NULL DEFAULT '' COMMENT '省份',
#   `city` varchar(64) NOT NULL DEFAULT '' COMMENT '城市',
#   `tag` varchar(64) NOT NULL DEFAULT '' COMMENT '所属标签',
#   `url` varchar(256) NOT NULL DEFAULT '' COMMENT '政策链接',
#   `title` text NOT NULL COMMENT '政策标题',
#   `content` longtext NOT NULL COMMENT '政策正文',
#   `published` timestamp NULL DEFAULT NULL COMMENT '发布时间',
#   `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
#   PRIMARY KEY (`id`),
#   KEY `policy_url` (`url`) USING BTREE
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


# CREATE TABLE `source` (
#   `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
#   `domain` varchar(128) NOT NULL COMMENT '来源domain',
#   `name` varchar(256) NOT NULL COMMENT '来源站点名称',
#   `country` tinyint(1) NOT NULL DEFAULT '0' COMMENT '中央级政府机关',
#   `province` varchar(32) NOT NULL DEFAULT '' COMMENT '省份',
#   `city` varchar(32) NOT NULL DEFAULT '' COMMENT '城市',
#   `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '加入时间',
#   PRIMARY KEY (`id`),
#   KEY `source_url` (`domain`) USING BTREE
# ) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;






