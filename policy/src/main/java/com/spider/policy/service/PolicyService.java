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

    int insertSourceList(int sourceId, String url, String tag, String header, String regular, int monitor);
}
