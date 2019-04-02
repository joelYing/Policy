package com.spider.policy.web;

import com.github.pagehelper.PageHelper;
import com.spider.policy.entity.*;
import com.spider.policy.mapper.PolicyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @PackageName com.spider.kan360.web
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
    public List<Policy> mulConditions(int page) {
        PageHelper.startPage(page, 20);
        return policyMapper.getAllPolicies();
    }

//    /**
//     * 可按照年代、类型、地区、影片名称 模糊匹配 多条件查询获取影片列表，可按照创建时间、更新时间 自定义排序方式 kan_allvedio
//     * value = "", required = true 就是必须要有(默认为true)
//     *
//     * ： /mulConditions?vedioName=海王&types=&year=&area=大陆&timeby=updatetime&rank=asc
//     */
//    @GetMapping("/mulConditions")
//    @ResponseBody
//    public List<VedioInfos> mulConditions(@RequestParam(value = "vedioName") String vedioName,
//                                          @RequestParam(value = "types") String types,
//                                          @RequestParam(value = "year") String year,
//                                          @RequestParam(value = "area") String area,
//                                          @RequestParam(value = "timeby") String timeby,
//                                          @RequestParam(value = "rank") String rank,
//                                          int page) {
//        PageHelper.startPage(page, 20);
//        return vedioMapper.getVedioByMulConditions(vedioName, types, year, area, timeby, rank);
//    }

}
