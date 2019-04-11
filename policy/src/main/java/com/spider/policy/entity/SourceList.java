package com.spider.policy.entity;

import lombok.Data;

/**
 * @PackageName com.spider.policy.entity
 * @Author joel
 * @Date 2019/4/10 15:27
 **/
@Data
public class SourceList {
    private int id;
    private int sourceId;
    private String url;
    private String tag;
    private String header;
    private int monitor;
}
