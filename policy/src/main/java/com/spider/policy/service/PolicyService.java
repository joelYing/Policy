package com.spider.policy.service;

import com.spider.policy.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PackageName com.spider.kan360.service
 * @Author joel
 * @Date 2019/2/2 11:36
 **/

public interface PolicyService {
    List<Policy> getAllPolicy(String title, String sourceName, String rank);

    int insertSourceList(int sourceId, String url, String tag, int useTool, String header,
                         String regular, String titleReg, String contentReg, String pageReg,
                         String timeReg, int pageStartNum, int pageLastNum, int monitor, int morePage);

    int insertSource(String domain, String name, int country, String province, String city);
}
