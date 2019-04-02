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
    private String policyUrl;
    private String policyTitle;
    private String policyContent;
    private String policySource;
    private String policyKeywords;
    private String publishTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp createTime;
}
