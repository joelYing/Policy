package com.spider.policy.service;

import com.spider.policy.entity.*;
import com.spider.policy.mapper.PolicyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * @PackageName com.spider.policy.service
 * @Author joel
 * @Date 2019/2/1 15:13
 **/

@Service
public class PolicyServiceImpl implements PolicyService {
    @Autowired
    public PolicyMapper policyMapper;

    @Override
    public List<Policy> getAllPolicy(String policyTitle, String policySource, String timeby, String rank) {
        return policyMapper.getAllPolicies(policyTitle, policySource, timeby, rank);
    }

    @Override
    public List<VedioInfos> getVedioByMulcondition(String vedioName, String types, String year, String area, String timeby, String rank) {
        return null;
    }

//    /**
//     * 可按照年代、类型、地区、影片名称模糊匹配多条件查询获取影片列表，创建时间排序
//     */
//    @Override
//    public List<VedioInfos> getVedioByMulcondition(String vedioName, String types, String year, String area, String timeby, String rank) {
//        return vedioMapper.getVedioByMulConditions(vedioName, types, year, area, timeby, rank);
//    }

}
