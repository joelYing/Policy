package com.spider.policy.service;

import com.spider.policy.entity.*;
import java.util.List;

/**
 * @PackageName com.spider.kan360.service
 * @Author joel
 * @Date 2019/2/2 11:36
 **/


public interface PolicyService {
    List<Policy> getAllPolicy();

    List<VedioInfos> getVedioByMulcondition(String vedioName, String types, String year,
                                            String area, String timeby, String rank);
}
