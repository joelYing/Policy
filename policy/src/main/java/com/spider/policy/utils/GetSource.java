package com.spider.policy.utils;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import com.github.suosi.commons.helper.utils.Strtotime;
import com.github.suosi.commons.spider.extract.content.webcollector.Article;
import com.github.suosi.commons.spider.extract.content.webcollector.ContentExtract;
import com.github.suosi.commons.spider.extract.site.PageExtract;
import com.github.suosi.commons.spider.extract.site.meta.Page;
import com.github.suosi.commons.spider.utils.CharsetUtils;
import com.github.suosi.commons.spider.utils.OkHttpUtils;
import com.github.suosi.commons.spider.utils.UrlUtils;
import com.spider.policy.bases.GetHtml;
import com.spider.policy.bases.GetPage;
import com.spider.policy.entity.Policy;
import com.spider.policy.entity.Source;
import com.spider.policy.entity.SourceList;
import okhttp3.Response;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @PackageName com.spider.policy.utils
 * @Author joel
 * @Date 2019/4/2 16:23
 **/

public class GetSource {
    private static int limitYear = 2009;
    private static String titleReg1 = "<title>([\\s\\S]*?)</title>";

    public static Timestamp formatTime(String time) {
        String result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Strtotime.parse(time) * 1000));
        System.out.println(result);
        return Timestamp.valueOf(result);
    }

    // 监控列表
    public static void getMonitorLists() {
        ArrayList<SourceList> sourceListArrayList = InfoSave.selectMonitorList();
        for(SourceList sourceList : sourceListArrayList) {
            if (sourceList.getUseTool() == 1) {
                getByTools(sourceList);
            }
            if (sourceList.getUseTool() == 0) {
                getByGenerals(sourceList);
            }
//            break;
        }
    }

    public static void getSourceLists() {
        ArrayList<SourceList> sourceListArrayList = InfoSave.selectSourceList();
//        for(SourceList sourceList : sourceListArrayList) {
//            if (sourceList.getUseTool() == 1) {
//                getByTools(sourceList);
//            }
//            if (sourceList.getUseTool() == 0) {
//                getByGenerals(sourceList);
//            }
////            break;
//        }
        for (int i = 0;i < sourceListArrayList.size();i++) {
            if (i == 8) {
                System.out.println(sourceListArrayList.get(i).getUrl());
                if (sourceListArrayList.get(i).getUseTool() == 1) {
                    getByTools(sourceListArrayList.get(i));
                }
                if (sourceListArrayList.get(i).getUseTool() == 0) {
                    getByGenerals(sourceListArrayList.get(i));
                }
            }
        }
    }

    public static void getByTools(SourceList sourceList) {
        String url = sourceList.getUrl();
        Page page;
        if (sourceList.getMorePage() == 0) {
            page = PageExtract.url(url);
            pageTool(sourceList, page);
        }
        if (sourceList.getMorePage() == 1) {
            page = PageExtract.url(url.replace("%s", String.valueOf(sourceList.getPageStartNum())));
            if (!"".equals(sourceList.getPageReg())) {
                Matcher matcher = Pattern.compile(sourceList.getPageReg()).matcher(page.getHtml());
                while (matcher.find()) {
                    String totalPage = matcher.group(1);
                    for (int p = sourceList.getPageStartNum(); p < Integer.parseInt(totalPage); p++) {
                        System.out.println(p);
                        Page pageMore = PageExtract.url(url.replace("%s", String.valueOf(p)));
                        pageTool(sourceList, pageMore);
                    }
                }
            } else if (sourceList.getPageLastNum() != 0) {
                for (int p = sourceList.getPageStartNum(); p < sourceList.getPageLastNum(); p++) {
                    System.out.println(p);
                    Page pageMore = PageExtract.url(url.replace("%s", String.valueOf(p)));
                    pageTool(sourceList, pageMore);
                }
            } else {
                System.out.println("error");
            }

        }
    }

    public static void getByGenerals(SourceList sourceList) {
//        String realHtml;
//        if (sourceList.getMorePage() == 0) {
//            if (!"".equals(sourceList.getHeader())) {
//                String headers = sourceList.getHeader();
//                String hk = headers.split(":")[0];
//                String hv = headers.split(":")[1];
//
//                realHtml = GetHtml.getHtml(sourceList.getUrl(), hk, hv);
//                Matcher matcher = Pattern.compile(sourceList.getRegular()).matcher(realHtml);
//
//                while (matcher.find()) {
//                    String policyUrl = "http://www.chinatax.gov.cn" + matcher.group(1);
//                    System.out.println(policyUrl);
//                    System.out.println(matcher.group(2));
//
//                }
//            }
//
//        }
//        if (sourceList.getMorePage() == 1) {
//            page = PageExtract.url(String.format(url, sourceList.getPageStartNum()));
//            if (!"".equals(sourceList.getPageReg())) {
//                Matcher matcher = Pattern.compile(sourceList.getPageReg()).matcher(page.getHtml());
//                while (matcher.find()) {
//                    String totalPage = matcher.group(1);
//                    for (int p = sourceList.getPageStartNum(); p < Integer.parseInt(totalPage); p++) {
//                        System.out.println(p);
//                        Page pageMore = PageExtract.url(String.format(url, p));
//                        pageTool(sourceList, pageMore);
//                    }
//                }
//            } else if (sourceList.getPageLastNum() != 0) {
//                for (int p = sourceList.getPageStartNum(); p < sourceList.getPageLastNum(); p++) {
//                    System.out.println(p);
//                    Page pageMore = PageExtract.url(String.format(url, p));
//                    pageTool(sourceList, pageMore);
//                }
//            } else {
//                System.out.println("error");
//            }
//
//        }
//
//        String content = "<li class=\"sv_texth3\" id=\"tax_content\">([\\s\\S]*?)</li>";
//        String time = "税务总局[\\s\\S]*?(\\d+)年(\\d+)月(\\d+)日";
//        for(int i = 1; i <= pages; i++) {
//            String realHtml;
//            if(i == 1) {
//                realHtml = GetHtml.getHtml5(sourceUrl + "n810341/n810755/index.html");
//            } else {
//                realHtml = GetHtml.getHtml5(sourceUrl + String.format("n810341/n810755/index_3849171_%s.html", i - 1));
//            }
//            Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//            while (matcher.find()) {
//                String policyUrl = "http://www.chinatax.gov.cn" + matcher.group(1);
//                System.out.println(policyUrl);
//                System.out.println(matcher.group(2));
//
//                policy.setPolicyUrl(policyUrl);
//                policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", ""));
//                policy.setPolicySource(sourceName);
//                policy.setPolicyKeywords("");
//
//                policy.setPolicyContent("");
//                try {
//                    String policyHtml = GetHtml.getHtml5(policyUrl);
//                    Matcher matcher1 = Pattern.compile(time).matcher(policyHtml);
//                    while (matcher1.find()) {
//                        String times = matcher1.group(1) + "-" + matcher1.group(2) + "-" + matcher1.group(3);
//                        policy.setPublishTime(times.trim());
//                    }
//                    Matcher matcher2 = Pattern.compile(content).matcher(policyHtml);
//                    while (matcher2.find()) {
//                        String text = matcher2.group(1).replaceAll("<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
//                        policy.setPolicyContent(text.trim());
//                    }
//                } catch (Exception e) {
//                    System.out.println("404");
//                }
//                InfoSave.insert(policy);
//            }
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static void parseHtml(SourceList sourceList, String realHtml, String realLink, Policy policy, Source source) {
        Matcher matcher1 = Pattern.compile(sourceList.getTitleReg()).matcher(realHtml);
        while (matcher1.find()) {
            String title = matcher1.group(1).trim();
            System.out.println(title);
            policy.setTitle(title);
        }
        Matcher matcher2 = Pattern.compile(sourceList.getContentReg()).matcher(realHtml);
        while (matcher2.find()) {
            String content = matcher2.group(1).replaceAll("<!--[\\s\\S]*?-->|<style[\\s\\S]*?/style>|<[\\s\\S]*?>|&.*?;|\\s", "").trim();
            System.out.println(content);
            policy.setContent(content);
        }
        Matcher matcher3 = Pattern.compile(sourceList.getTimeReg()).matcher(realHtml);
        while (matcher3.find()) {
            String time = matcher3.group(1) + "-" + matcher3.group(2) + "-" + matcher3.group(3);
            // 格式化时间
            Timestamp formatTime = formatTime(time);
            policy.setPublished(formatTime);
            break;
        }
        policy.setSourceId(sourceList.getSourceId());
        policy.setSourceName(source.getName());
        policy.setCountry(source.getCountry());
        policy.setProvince(source.getProvince());
        policy.setCity(source.getCity());
        policy.setTag(sourceList.getTag());
        policy.setUrl(realLink);

        InfoSave.insert(policy);
    }

    public static void pageTool(SourceList sourceList, Page page) {
        Source source = InfoSave.selectSourceId(sourceList.getSourceId());
        if (page != null) {
            Set<String> links = page.getLinks();
            if (links != null) {
                for (String link : links) {
                    Policy policy = new Policy();
                    if (UrlUtils.guessArticleUrl(link, null) || UrlUtils.guessListUrl(link, null)) {

                        String regular = link;
                        if (!"".equals(sourceList.getRegular())) {
                            regular = sourceList.getRegular();
                        }
                        Matcher matcher = Pattern.compile(regular).matcher(link);
                        while (matcher.find()) {
                            String realLink = matcher.group();
                            System.out.println("A -> " + realLink);
                            if (!"".equals(sourceList.getHeader())) {
                                String headers = sourceList.getHeader();
                                String hk = headers.split(":")[0];
                                String hv = headers.split(":")[1];

                                String realHtml = GetHtml.getHtml5(realLink, hk, hv);
                                parseHtml(sourceList, realHtml, realLink, policy, source);
                            } else {
                                try (Response response = OkHttpUtils.client().newCall(OkHttpUtils.request(realLink)).execute()) {
                                    System.out.println(response.code());
                                    if (response.isSuccessful() && response.body() != null) {
                                        byte[] bytes = response.body().bytes();
                                        String charset = CharsetUtils.guessCharset(bytes, response);
                                        String html = new String(bytes, charset);
                                        parseHtml(sourceList, html, realLink, policy, source);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        getSourceLists();
    }
}
