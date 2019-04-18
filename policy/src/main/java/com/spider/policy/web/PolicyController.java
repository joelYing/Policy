package com.spider.policy.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spider.policy.entity.*;
import com.spider.policy.mapper.PolicyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @PackageName com.spider.policy.web
 * @Author joel
 * @Date 2019/2/1 15:32
 **/

//@RestController
@Controller
@RequestMapping("/")
public class PolicyController {
    @Autowired
    private PolicyMapper policyMapper;

//    @GetMapping("/index")
//    public String indexForm(Model model){
//        model.addAttribute("postField", new PostField());
//        return "indexform";
//    }
//
//    @PostMapping("/index")
//    public String indexSubmit(Model model, @ModelAttribute PostField postField){
//        model.addAttribute(postField);
//        return "indexresult";
//    }

    @GetMapping("/index")
    public ModelAndView index(ModelAndView modelAndView, HttpServletResponse response){
        PostField postField = new PostField();
        postField.setPolicyTitle("");
        postField.setPolicySource("");
        postField.setRank("desc");

        modelAndView.addObject("postField", postField);
        modelAndView.addObject("response", response);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/allPolicy")
    public ModelAndView mulConditions(@RequestParam(value = "title") String title,
                                      @RequestParam(value = "sourceName") String sourcename,
                                      @RequestParam(value = "rank") String rank, int page,
                                      HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        String baseUrl = request.getRequestURL() + "?" + request.getQueryString().split("page")[0] + "page=";
        PageHelper.startPage(page, 20);
        // http://127.0.0.1:8080/allPolicy?policyTitle=%E5%AE%81%E6%B3%A2&policySource=&timeby=publish_time&rank=desc&page=2
        List<Policy> policyList = policyMapper.getAllPolicies(title, sourcename, rank);
        PageInfo<Policy> policyPageInfo = new PageInfo<>(policyList);
        mav.addObject("policyPageInfo", policyPageInfo);
        mav.addObject("baseUrl", baseUrl);
        mav.setViewName("policy");

//        return policyMapper.getAllPolicies(policyTitle, policySource, timeby, rank);
        return mav;
    }

    @RequestMapping("/insertSourceList")
    @ResponseBody
    public int insertParam(@RequestParam(value = "sourceId") int sourceId,
                           @RequestParam(value = "url") String url,
                           @RequestParam(value = "tag") String tag,
                           @RequestParam(value = "header") String header,
                           @RequestParam(value = "regular") String regular,
                           @RequestParam(value = "monitor") int monitor) {
        return policyMapper.insertSourceList(sourceId, url, tag, header, regular, monitor);
    }

}
