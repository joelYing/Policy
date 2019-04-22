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
#   `content` longtext COMMENT '政策正文',
#   `published` timestamp NULL DEFAULT NULL COMMENT '发布时间',
#   `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
#   PRIMARY KEY (`id`),
#   KEY `policy_url` (`url`) USING BTREE
# ) ENGINE=InnoDB AUTO_INCREMENT=18558 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


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
# ) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


# CREATE TABLE `source_list` (
#   `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
#   `source_id` int(10) unsigned NOT NULL COMMENT '信源ID',
#   `url` varchar(512) NOT NULL COMMENT '内容页采集链接',
#   `tag` varchar(64) NOT NULL COMMENT '内容页标签',
#   `use_tool` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否使用工具；1表示使用',
#   `header` varchar(256) NOT NULL COMMENT '需要的头部信息',
#   `regular` varchar(256) NOT NULL DEFAULT '' COMMENT '精确筛选正则表达式',
#   `title_reg` varchar(256) NOT NULL DEFAULT '' COMMENT '标题正则',
#   `content_reg` varchar(256) NOT NULL DEFAULT '' COMMENT '正文正则',
#   `page_reg` varchar(256) NOT NULL DEFAULT '' COMMENT '页数正则',
#   `time_reg` varchar(256) NOT NULL DEFAULT '' COMMENT '发布时间正则',
#   `page_startnum` int(4) unsigned NOT NULL COMMENT '起始页码',
#   `page_lastnum` int(4) unsigned NOT NULL COMMENT '最后页码',
#   `monitor` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '监控页面',
#   `morepage` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '首次采集判断是否多页',
#   PRIMARY KEY (`id`)
# ) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb4;







