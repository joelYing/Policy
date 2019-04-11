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
    private int useTool;
    private String header;
    private String regular;
    private String titleReg;
    private String contentReg;
    private String pageReg;
    private String timeReg;
    private int pageStartNum;
    private int pageLastNum;
    private int monitor;
    private int morePage;
}
