package com.spider.policy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.sql.Timestamp;

/**
 * @PackageName com.spider.policy.entity
 * @Author joel
 * @Date 2019/4/11 15:31
 **/
@Data
public class Source {
    private int id;
    private String domain;
    private String name;
    private int country;
    private String province;
    private String city;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp createTime;
}
