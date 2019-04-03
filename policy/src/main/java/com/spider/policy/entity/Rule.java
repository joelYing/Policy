package com.spider.policy.entity;

import lombok.Data;

/**
 * @PackageName com.spider.policy.entity
 * @Author joel
 * @Date 2019/4/3 9:52
 **/
@Data
public class Rule {
    private String domain;
    private String sourceName;
    private String matchRule;
}
