package com.spider.policy.web;

import com.github.pagehelper.PageHelper;
import com.spider.policy.entity.*;
import com.spider.policy.mapper.PolicyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @PackageName com.spider.policy.web
 * @Author joel
 * @Date 2019/2/1 15:32
 **/

@RestController
@RequestMapping("/")
public class PolicyController {
    @Autowired
    private PolicyMapper policyMapper;

    @GetMapping("/allPolicy")
    @ResponseBody
    public List<Policy> mulConditions(@RequestParam(value = "policyTitle") String policyTitle,
                                      @RequestParam(value = "policySource") String policySource,
                                      @RequestParam(value = "timeby") String timeby,
                                      @RequestParam(value = "rank") String rank, int page) {
        PageHelper.startPage(page, 20);
        // http://127.0.0.1:8080/allPolicy?policyTitle=%E5%AE%81%E6%B3%A2&policySource=&timeby=publish_time&rank=desc&page=2
        return policyMapper.getAllPolicies(policyTitle, policySource, timeby, rank);
    }

}
