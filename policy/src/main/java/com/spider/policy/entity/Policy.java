package com.spider.policy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.sql.Timestamp;

/**
 * @PackageName com.spider.kan360.entity
 * @Author joel
 * @Date 2019/4/2 16:08
 **/
@Data
public class Policy {
    private int id;
    private int sourceId;
    private String sourceName;
    private int country;
    private String province;
    private String city;
    private String tag;
    private String url;
    private String title;
    private String content;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp published;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp createTime;

}
