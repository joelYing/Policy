package com.spider.policy.service.impl;

import com.spider.policy.entity.*;
import com.spider.policy.mapper.PolicyMapper;
import com.spider.policy.service.PolicyService;
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
    public List<Policy> getAllPolicy(String title, String sourceName,String rank) {
        return policyMapper.getAllPolicies(title, sourceName, rank);
    }

    @Override
    public int insertSourceList(int sourceId, String url, String tag, int useTool, String header,
                                String regular, String titleReg, String contentReg, String pageReg,
                                String timeReg, int pageStartNum, int pageLastNum, int monitor, int morePage) {
        return policyMapper.insertSourceList(sourceId, url, tag, useTool, header, regular, titleReg,
                 contentReg, pageReg, timeReg, pageStartNum, pageLastNum, monitor, morePage);
    }

    @Override
    public int insertSource(String domain, String name, int country, String province, String city) {
        return policyMapper.insertSource(domain, name, country, province, city);
    }

}
