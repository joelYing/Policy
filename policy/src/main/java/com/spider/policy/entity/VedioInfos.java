package com.spider.policy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.sql.Timestamp;

/**
 * @PackageName com.spider.kan360.entity
 * @Author joel
 * @Date 2019/2/15 10:41
 **/

@Data
public class VedioInfos {
    /**
     *  视频 基本信息
     * "@Data" 注解在 类 上；提供类所有属性的 get 和 set 方法，此外还提供了equals、canEqual、hashCode、toString 方法
     */
    private int id;
    private String vedioId;
    private String vedioUrl;
    private String vedioType;
    private String vedioName;
    private String coverImg;
    private String pay;
    private String score;
    private String types;
    private String year;
    private String area;
    private String daoyan;
    private String yanyuan;
    private String introduce;
    private String source;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp updateTime;
}
