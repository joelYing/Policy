package com.spider.policy.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spider.policy.entity.*;
import com.spider.policy.mapper.PolicyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @PackageName com.spider.policy.web
 * @Author joel
 * @Date 2019/2/1 15:32
 **/

@Controller
@RequestMapping("/")
public class PolicyController {


    @Autowired
    private PolicyMapper policyMapper;

    @GetMapping("/insertSource")
    public String insertSource(Model model) {
        model.addAttribute("source", new Source());
        return "insertSource";
    }

    @PostMapping("/insertSource")
    public String insertSourceSubmit(Model model, @ModelAttribute Source source) {
        policyMapper.insertSource(source.getDomain(), source.getName(), source.getCountry(), source.getProvince(),
                source.getCity());

        model.addAttribute("source", source);
        return "result";
    }

    @GetMapping("/insertSourceList")
    public String insertSourceList(Model model) {
        model.addAttribute("sourceList", new SourceList());
        return "insertSourceList";
    }

    @PostMapping("/insertSourceList")
    public String insertSourceListSubmit(Model model, @ModelAttribute SourceList sourceList) {
        policyMapper.insertSourceList(sourceList.getSourceId(), sourceList.getUrl(), sourceList.getTag(),
                sourceList.getUseTool(), sourceList.getHeader(), sourceList.getRegular(), sourceList.getTitleReg(),
                sourceList.getContentReg(), sourceList.getPageReg(), sourceList.getTimeReg(), sourceList.getPageStartNum(),
                sourceList.getPageLastNum(), sourceList.getMonitor(), sourceList.getMorePage());

        model.addAttribute("sourceList", sourceList);
        return "resultList";
    }

    @RequestMapping("/preview")
    public String preview(@Param("url") String url, Model model) {
        Policy policy = policyMapper.getPolicy(url);
        model.addAttribute("policy", policy);
        return "preview";
    }

    @GetMapping("/queryPolicy")
    public String mulConditions(Model model) {
        model.addAttribute("postField", new PostField());
        return "query";
    }

    private PostField postField = null;

    @RequestMapping("/allPolicy")
    public String mulConditions(Model model, @ModelAttribute PostField field, HttpServletRequest request) {
        if (field.getRank()!=null){
            postField = field;
        }
        String pageNo = request.getQueryString();
        if (pageNo==null){
            pageNo = "page=1";
        }
        pageNo = pageNo.replace("page=","");
        Integer pageNum = Integer.parseInt(pageNo);

        PageHelper.startPage(pageNum, 20);
        List<Policy> policyList = policyMapper.getAllPolicies(postField.getPolicyTitle(),
                postField.getPolicySource(), postField.getRank());
        PageInfo<Policy> policyPageInfo = new PageInfo<>(policyList);
        model.addAttribute("policyPageInfo", policyPageInfo);
        return "policy";
    }

//    @GetMapping("/allPolicy")
//    public ModelAndView mulConditions(@RequestParam(value = "title") String title,
//                                      @RequestParam(value = "sourceName") String sourcename,
//                                      @RequestParam(value = "rank") String rank, int page,
//                                      HttpServletRequest request) {
//        ModelAndView mav = new ModelAndView();
//        String baseUrl = request.getRequestURL() + "?" + request.getQueryString().split("page")[0] + "page=";
//        PageHelper.startPage(page, 20);
//        // http://127.0.0.1:8080/allPolicy?policyTitle=%E5%AE%81%E6%B3%A2&policySource=&timeby=publish_time&rank=desc&page=2
//        List<Policy> policyList = policyMapper.getAllPolicies(title, sourcename, rank);
//        PageInfo<Policy> policyPageInfo = new PageInfo<>(policyList);
//        mav.addObject("policyPageInfo", policyPageInfo);
//        mav.addObject("baseUrl", baseUrl);
//        mav.setViewName("policy");
//        return mav;
//    }
}
