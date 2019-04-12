package com.spider.policy.utils;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
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
import com.spider.policy.entity.SourceList;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
            if (i == 20) {
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
            page = PageExtract.url(String.format(url, sourceList.getPageStartNum()));
            if (!"".equals(sourceList.getPageReg())) {
                Matcher matcher = Pattern.compile(sourceList.getPageReg()).matcher(page.getHtml());
                while (matcher.find()) {
                    String totalPage = matcher.group(1);
                    for (int p = sourceList.getPageStartNum(); p < Integer.parseInt(totalPage); p++) {
                        System.out.println(p);
                        Page pageMore = PageExtract.url(String.format(url, p));
                        pageTool(sourceList, pageMore);
                    }
                }
            } else if (sourceList.getPageLastNum() != 0) {
                for (int p = sourceList.getPageStartNum(); p < sourceList.getPageLastNum(); p++) {
                    System.out.println(p);
                    Page pageMore = PageExtract.url(String.format(url, p));
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

    public static void pageTool(SourceList sourceList, Page page) {
        Policy policy = new Policy();
        if (page != null) {
            Set<String> links = page.getLinks();
            if (links != null) {
                for (String link : links) {
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
                                Matcher matcher1 = Pattern.compile(sourceList.getTitleReg()).matcher(realHtml);
                                while (matcher1.find()) {
                                    String title = matcher1.group(1).trim();
                                    System.out.println(title);
                                }
                                Matcher matcher2 = Pattern.compile(sourceList.getContentReg()).matcher(realHtml);
                                while (matcher2.find()) {
                                    String content = matcher2.group(1).replaceAll("<.*?>|&.*?;|\\s", "").trim();
                                    System.out.println(content);
                                }
                                Matcher matcher3 = Pattern.compile(sourceList.getTimeReg()).matcher(realHtml);
                                while (matcher3.find()) {
                                    String time = matcher3.group(1) + "-" + matcher3.group(2) + "-" + matcher3.group(3);
                                    System.out.println(time);
                                }

                            } else {
                                try (Response response = OkHttpUtils.client().newCall(OkHttpUtils.request(realLink)).execute()) {
                                    System.out.println(response.code());
                                    if (response.isSuccessful() && response.body() != null) {
                                        byte[] bytes = response.body().bytes();
                                        String charset = CharsetUtils.guessCharset(bytes, response);
                                        String html = new String(bytes, charset);

                                        Matcher matcher1 = Pattern.compile(sourceList.getTitleReg()).matcher(html);
                                        while (matcher1.find()) {
                                            String title = matcher1.group(1).trim();
                                            System.out.println(title);
                                        }
                                        Matcher matcher2 = Pattern.compile(sourceList.getContentReg()).matcher(html);
                                        while (matcher2.find()) {
                                            String content = matcher2.group(1).replaceAll("<.*?>|&.*?;|\\s", "").trim();
                                            System.out.println(content);
                                        }
                                        Matcher matcher3 = Pattern.compile(sourceList.getTimeReg()).matcher(html);
                                        while (matcher3.find()) {
                                            String time = matcher3.group(1) + "-" + matcher3.group(2) + "-" + matcher3.group(3);
                                            System.out.println(time);
                                        }

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




//    public static void getSourcePolicy(String sourceName, String sourceUrl, String sourceRule) {
//        Policy policy = new Policy();
//        if("中华人民共和国商务部".equals(sourceName)) {
//            mofcom(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("中华人民共和国外交部".equals(sourceName)) {
//            fmprc(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("国家市场监督管理总局".equals(sourceName)) {
//            samrsaic(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("国家税务总局".equals(sourceName)) {
//            chinatax(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("国家统计局".equals(sourceName)) {
//            stats(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("中华人民共和国国家发展和改革委员会".equals(sourceName)) {
//            ndrc(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("中华人民共和国司法部".equals(sourceName)) {
//            moj(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("中华人民共和国人力资源和社会保障部".equals(sourceName)) {
//            mohrss(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("中华人民共和国交通运输部".equals(sourceName)) {
//            mot(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("中华人民共和国工业和信息化部".equals(sourceName)) {
//            // 未结束 但只采6页
//            miit(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("中华人民共和国财政部".equals(sourceName)) {
//            mof(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("中华人民共和国审计署".equals(sourceName)) {
//            audit(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("国务院国有资产监督管理委员会".equals(sourceName)) {
//            sasac(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("中华人民共和国海关总署".equals(sourceName)) {
//            // 会有403
//            customs(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("国家机关事务管理局".equals(sourceName)) {
//            ggj(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("国务院港澳事务办公室".equals(sourceName)) {
//            hmo(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("中国证券监督管理委员会".equals(sourceName)) {
//            csrc(policy, sourceUrl, sourceName, sourceRule);
//        }
////        if("中国银行保险监督管理委员会".equals(sourceName)) {
////            cbrc(policy, sourceUrl, sourceName, sourceRule);
////        }
//        if("中国民用航空局".equals(sourceName)) {
//            caac(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("国家铁路局".equals(sourceName)) {
//            nra(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("国家邮政局".equals(sourceName)) {
//            // 会有403反爬
//            spb(policy, sourceUrl, sourceName, sourceRule);
//        }
//    }

//    public static void mofcom(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        int pages = 5;
//        String content = "<!--文章正文-->([\\s\\S]*)<div class=\"artLabel\">";
//        String[] mofcoms = {"c", "fwzl", "d", "e", "f", "bf", "xxfb"};
//        for(String tag : mofcoms) {
//            for(int i = 1; i <= pages; i++) {
//                String realHtml;
//                if(i == 1) {
//                    realHtml = GetHtml.getHtml(sourceUrl + String.format("article/b/%s/?", tag));
//                } else {
//                    realHtml = GetHtml.getHtml(sourceUrl + String.format("article/b/%s/?%s", tag, i));
//
//                }
//                Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//                while (matcher.find()) {
//                    if(Integer.parseInt(matcher.group(3)) > limitYear) {
//                        String policyUrl = "http://www.mofcom.gov.cn" + matcher.group(2);
//                        System.out.println(policyUrl);
//                        System.out.println(matcher.group(1));
//
//                        String policyHtml = GetHtml.getHtml(policyUrl);
//                        Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                        while (matcher1.find()) {
//                            // <(?!img|iframe).*?>
//                            String text = matcher1.group(1).replaceAll("<.*?>|&nbsp;|currentpage=1;", "");
//                            policy.setPolicyContent(text.trim());
//                        }
//                        policy.setPolicyUrl(policyUrl);
//                        policy.setPolicyTitle(matcher.group(1).replaceAll("&.*?;", ""));
//                        policy.setPolicySource(sourceName);
//                        policy.setPolicyKeywords("");
//                        policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
//                        InfoSave.insert(policy);
//                    }
//                }
//            }
//        }
//    }
//
//    public static void fmprc(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        int pages = 3;
//        String content = "<div id=\"News_Body_Txt_A\" class=\"content\">([\\s\\S]*?)</div>";
//        for(int i = 1; i <= pages; i++) {
//            String realHtml;
//            if(i == 1) {
//                realHtml = GetHtml.getHtml(sourceUrl + "web/ziliao_674904/tytj_674911/zcwj_674915/default.shtml");
//            } else {
//                realHtml = GetHtml.getHtml(sourceUrl + String.format("web/ziliao_674904/tytj_674911/zcwj_674915/default_%s.shtml", i - 1));
//            }
//            Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//            while (matcher.find()) {
//                if(Integer.parseInt(matcher.group(3)) > limitYear) {
//                    String policyUrl = "https://www.fmprc.gov.cn/web/ziliao_674904/tytj_674911/zcwj_674915" + matcher.group(1);
//                    System.out.println(policyUrl);
//                    System.out.println(matcher.group(2));
//
//                    policy.setPolicyUrl(policyUrl);
//                    policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", ""));
//                    policy.setPolicySource(sourceName);
//                    policy.setPolicyKeywords("");
//                    policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
//                    policy.setPolicyContent("");
//                    if(!policyUrl.contains("pdf")) {
//                        String policyHtml = GetHtml.getHtml(policyUrl);
//                        Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                        while (matcher1.find()) {
//                            String text = matcher1.group(1).replaceAll("<.*?>|&.*?;|\\s", "");
//                            policy.setPolicyContent(text.trim());
//                        }
//                    }
//
//                    InfoSave.insert(policy);
//                }
//            }
//        }
//    }
//
//    public static void samrsaic(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        int pages = 5;
//        String content = "<div class=\"Three_xilan_02\">([\\s\\S]*?)</div>";
//        for(int i = 1; i <= pages; i++) {
//            String realHtml;
//            if(i == 1) {
//                realHtml = GetHtml.getHtml("http://gkml.samr.gov.cn/2140/2170/list.html");
//            } else {
//                realHtml = GetHtml.getHtml(String.format("http://gkml.samr.gov.cn/2140/2170/list_%s.html", i - 1));
//            }
//            Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//            while (matcher.find()) {
//                if(Integer.parseInt(matcher.group(3)) > limitYear) {
//                    String policyUrl = "http://gkml.samr.gov.cn" + matcher.group(1);
//                    System.out.println(policyUrl);
//                    System.out.println(matcher.group(2));
//
//                    policy.setPolicyUrl(policyUrl);
//                    policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", ""));
//                    policy.setPolicySource(sourceName);
//                    policy.setPolicyKeywords("");
//                    policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
//                    policy.setPolicyContent("");
//                    String policyHtml = GetHtml.getHtml(policyUrl);
//                    Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                    while (matcher1.find()) {
//                        String text = matcher1.group(1).replaceAll("<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
//                        policy.setPolicyContent(text.trim());
//                    }
//                    InfoSave.insert(policy);
//                }
//            }
//        }
//    }
//
//    // 添加cookie
//    public static void chinatax(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        int pages = 2;
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
//    }
//
//    public static void stats(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        int pages = 5;
//        String content = "<div class=\"content\".*?>[\\s\\S]*?<span.*?>([\\s\\S]*?)</div>";
//        String time = "（.*?<span lang=\"EN-US\">(\\d{4}).*?年<span lang=\"EN-US\">(\\d+).*?月<span lang=\"EN-US\">(\\d+).*?日.*?）";
//        for(int i = 1; i <= pages; i++) {
//            String realHtml;
//            if(i == 1) {
//                realHtml = GetHtml.getHtml("http://www.stats.gov.cn/statsinfo/32/42/48/list.htm");
//            } else if(i == 2) {
//                realHtml = GetHtml.getHtml("http://www.stats.gov.cn/statsinfo/32/42/48/list_1.htm");
//            } else {
//                realHtml = GetHtml.getHtml(String.format("http://www.stats.gov.cn/statsinfo/32/42/4%s/list.htm", i));
//            }
//            if(i > 2) {
//                Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//                while (matcher.find()) {
//                    if(Integer.parseInt(matcher.group(3)) > limitYear) {
//                        String policyUrl = "http://www.stats.gov.cn/statsinfo" + matcher.group(1);
//                        System.out.println(policyUrl);
//                        System.out.println(matcher.group(2));
//
//                        policy.setPolicyUrl(policyUrl);
//                        policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", ""));
//                        policy.setPolicySource(sourceName);
//                        policy.setPolicyKeywords("");
//                        policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
//                        policy.setPolicyContent("");
//                        String policyHtml = GetHtml.getHtml(policyUrl);
//                        Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                        while (matcher1.find()) {
//                            String text = matcher1.group(1).replaceAll("<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->|<style type=\"text/css\">([\\s\\S]*?)</style>", "");
//                            policy.setPolicyContent(text.trim());
//                        }
//                        InfoSave.insert(policy);
//                    }
//                }
//            }
//            if(i <= 2) {
//                String rule = "<li class=\"mc\">[\\s\\S]*?<a href=\"../../..(.*?)\" target=\"_blank\".*?>(.*?)</a>";
//                Matcher matcher = Pattern.compile(rule).matcher(realHtml);
//                while (matcher.find()) {
//                    String policyUrl = "http://www.stats.gov.cn/statsinfo" + matcher.group(1);
//                    System.out.println(policyUrl);
//                    System.out.println(matcher.group(2));
//
//                    policy.setPolicyUrl(policyUrl);
//                    policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", ""));
//                    policy.setPolicySource(sourceName);
//                    policy.setPolicyKeywords("");
//
//                    policy.setPolicyContent("");
//                    String policyHtml = GetHtml.getHtml(policyUrl);
//                    Matcher matcher2 = Pattern.compile(time).matcher(policyHtml);
//                    while (matcher2.find()) {
//                        String times = matcher2.group(1) + "-" + matcher2.group(2) + "-" + matcher2.group(3);
//                        policy.setPublishTime(times.trim());
//                    }
//                    Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                    while (matcher1.find()) {
//                        String text = matcher1.group(1).replaceAll("<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->|<style type=\"text/css\">([\\s\\S]*?)</style>", "");
//                        policy.setPolicyContent(text.trim());
//                    }
//                    InfoSave.insert(policy);
//                }
//            }
//        }
//    }
//
//    public static void ndrc(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        String content = "<div class=\"txt1\" id=\"zoom\">([\\s\\S]*)<ul class=\"txt_bottom clearfix\">";
//        String[] ndrcs = {"zcfbl", "gfxwj", "zcfbgg", "zcfbghwb", "zcfbtz", "zcfbqt"};
//        for(String tag : ndrcs) {
//            int pages = GetPage.getNdrcPage(GetHtml.getHtml(String.format("http://www.ndrc.gov.cn/zcfb/%s/index.html", tag)));
//            for(int i = 1; i <= pages; i++) {
//                String realHtml;
//                if(i == 1) {
//                    realHtml = GetHtml.getHtml(sourceUrl + String.format("zcfb/%s/index.html", tag));
//                } else {
//                    realHtml = GetHtml.getHtml(sourceUrl + String.format("zcfb/%s/index_%s.html", tag, i - 1));
//                }
//                Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//                while (matcher.find()) {
//                    if(Integer.parseInt(matcher.group(1)) > limitYear) {
//                        String policyUrl = String.format("http://www.ndrc.gov.cn/zcfb/%s", tag) + matcher.group(4);
//                        System.out.println(policyUrl);
//                        System.out.println(matcher.group(5));
//
//                        String policyHtml = GetHtml.getHtml(policyUrl);
//                        Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                        while (matcher1.find()) {
//                            // <(?!img|iframe).*?>
//                            String text = matcher1.group(1).replaceAll("<.*?>|&nbsp;|currentpage=1;", "");
//                            policy.setPolicyContent(text.trim());
//                        }
//                        policy.setPolicyUrl(policyUrl);
//                        policy.setPolicyTitle(matcher.group(5).replaceAll("&.*?;", ""));
//                        policy.setPolicySource(sourceName);
//                        policy.setPolicyKeywords("");
//                        policy.setPublishTime(matcher.group(1) + "-" + matcher.group(2) + "-" + matcher.group(3));
//                        InfoSave.insert(policy);
//                    }
//                }
//            }
//        }
//    }
//
//    public static void moj(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        String content = "<div class=\"con\" id=\"content\">([\\s\\S]*)</div> ";
//        String[] mojs = {"tzwj", "595", "594"};
//        for(String tag : mojs) {
//            int i = 1;
//            String realHtml = GetHtml.getHtml(sourceUrl + String.format("json/%s_1.json", tag));
//            Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//            while (matcher.find()) {
//                if (i < 100) {
//                    if(Integer.parseInt(matcher.group(3)) > limitYear) {
//                        String policyUrl = "http://www.moj.gov.cn" + matcher.group(1).replace("http://www.moj.gov.cn", "").trim();
//                        System.out.println(policyUrl);
//                        System.out.println(matcher.group(2));
//
//                        String policyHtml = GetHtml.getHtml(policyUrl);
//                        Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                        while (matcher1.find()) {
//                            // <(?!img|iframe).*?>
//                            String text = matcher1.group(1).replaceAll("<style.*?/script>|<.*?>|&nbsp;|currentpage=1;", "");
//                            policy.setPolicyContent(text.trim());
//                        }
//                        policy.setPolicyUrl(policyUrl);
//                        policy.setPolicyTitle(matcher.group(2)
//                                .replaceAll("&.*?;|<.*?>", "")
//                                .replace("\\u200B", "").replace("\\u200D", "")
//                                .replace("\\u201C", "“")
//                                .replace("\\u201D", "“")
//                                .replace("\\u2014", "-"));
//                        policy.setPolicySource(sourceName);
//                        policy.setPolicyKeywords("");
//                        policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
//                        InfoSave.insert(policy);
//                        i++;
//                    }
//                } else {
//                    break;
//                }
//            }
//        }
//    }
//
//    public static void mohrss(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        String content = "<div class=\"insMainConTxt_a\">([\\s\\S]*?)<script";
//        String[] mohrsss = {"bmgz", "gfxwj"};
//        for(String tag : mohrsss) {
//            int pages = GetPage.getMohrssPage(GetHtml.getHtml(String.format("http://www.mohrss.gov.cn/gkml/zcfg/%s/", tag)));
//            for (int i = 1; i <= pages; i++) {
//                String realHtml;
//                if (i == 1) {
//                    realHtml = GetHtml.getHtml(String.format("http://www.mohrss.gov.cn/gkml/zcfg/%s/index.html", tag));
//                } else {
//                    realHtml = GetHtml.getHtml(String.format("http://www.mohrss.gov.cn/gkml/zcfg/%s/index_%s.html", tag, i - 1));
//                }
//                Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//                while (matcher.find()) {
//                    if (Integer.parseInt(matcher.group(2)) > limitYear) {
//                        String policyUrl;
//                        String partUrl = matcher.group(1)
//                                .replace("../", "")
//                                .replace("./", "");
//                        if (partUrl.contains("SYrlzyhshbzb")) {
//                            policyUrl = "http://www.mohrss.gov.cn/" + partUrl;
//                        } else {
//                            policyUrl = String.format("http://www.mohrss.gov.cn/gkml/zcfg/%s/", tag) + partUrl;
//                        }
//
//                        System.out.println(policyUrl);
//                        System.out.println(matcher.group(5));
//
//                        policy.setPolicyUrl(policyUrl);
//                        policy.setPolicyTitle(matcher.group(5).replaceAll("&.*?;", ""));
//                        policy.setPolicySource(sourceName);
//                        policy.setPolicyKeywords("");
//                        policy.setPublishTime(matcher.group(2) + "-" + matcher.group(3) + "-" + matcher.group(4));
//                        policy.setPolicyContent("");
//                        String policyHtml = GetHtml.getHtml(policyUrl);
//                        Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                        while (matcher1.find()) {
//                            String text = matcher1.group(1).replaceAll("<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
//                            policy.setPolicyContent(text.trim());
//                        }
//                        InfoSave.insert(policy);
//                    }
//                }
//            }
//        }
//    }
//
//    public static void mot(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        String content = "id=\"content_main\".*?>([\\s\\S]*?)<script language=\"javascript\">";
//        int pages = 5;
//        for (int i = 1; i <= pages; i++) {
//            String realHtml;
//            realHtml = GetHtml.getHtml(String.format("http://was.mot.gov.cn:8080/govsearch/gov_list_new.jsp?page=%s", i));
//            Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//            while (matcher.find()) {
//                if (Integer.parseInt(matcher.group(3)) > limitYear) {
//                    String policyUrl = matcher.group(1);
//                    System.out.println(policyUrl);
//                    System.out.println(matcher.group(2));
//
//                    policy.setPolicyUrl(policyUrl);
//                    policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", ""));
//                    policy.setPolicySource(sourceName);
//                    policy.setPolicyKeywords("");
//                    policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
//                    policy.setPolicyContent("");
//                    try {
//                        String policyHtml = GetHtml.getHtml(policyUrl);
//                        Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                        while (matcher1.find()) {
//                            String text = matcher1.group(1).replaceAll("<style[\\s\\S]*?/style>|<!--[\\s\\S]*?-->|<.*?>|&.*?;|\\s", "");
//                            policy.setPolicyContent(text.trim());
//                        }
//                        InfoSave.insert(policy);
//                    } catch (Exception e) {
//                        System.out.println("error");
//                    }
//                }
//            }
//        }
//    }
//
//    // 限制访问一定时间内的次数 七页 112页之后是2011年的部分内容，未采集
//    public static void miit(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        int pages = 6;
//        String content = "<div class=\"ccontent center\".*?>([\\s\\S]*?)</div>";
//        for (int i = 1; i <= pages; i++) {
//            if (i > 0) {
//                System.out.println("this is : " + i);
//                String realHtml;
//                realHtml = GetHtml.getHtml("http://www.miit.gov.cn/gdnps/searchIndex.jsp?" +
//                        "params=%257B%2522goPage%2522%253A" + String.format("%s", i) + "%252C%2522orderBy%2522%253A%255B%257B%2522" +
//                        "orderBy%2522%253A%2522publishTime%2522%252C%2522reverse%2522%253" +
//                        "Atrue%257D%252C%257B%2522orderBy%2522%253A%2522orderTime%2522%252C%2522reverse%2522%253" +
//                        "Atrue%257D%255D%252C%2522pageSize%2522%253A10%252C%2522" +
//                        "queryParam%2522%253A%255B%257B%257D%252C%257B%257D%252C%257B%2522" +
//                        "shortName%2522%253A%2522fbjg%2522%252C%2522" +
//                        "value%2522%253A%2522%252F1%252F29%252F1146295%252F1652858%252F1652930%2522%257D%255D%257D");
//                Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//                while (matcher.find()) {
//                    if (Integer.parseInt(matcher.group(7)) > limitYear) {
////                    if (Integer.parseInt(matcher.group(3)) > limitYear) {
//                        String policyUrl = sourceUrl + String.format("n%s/n%s/n%s/n%s/c%s/content.html",
//                                matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5));
////                                  matcher.group(6), matcher.group(7), matcher.group(8), matcher.group(9), matcher.group(1));
//
//                        System.out.println(policyUrl);
//                        System.out.println(matcher.group(6));
////                        System.out.println(matcher.group(2));
//
//                        policy.setPolicyUrl(policyUrl);
//                        policy.setPolicyTitle(matcher.group(6).replaceAll("&.*?;|(\\\\u\\w{4})", ""));
//                        policy.setPolicySource(sourceName);
//                        policy.setPolicyKeywords("");
//                        policy.setPublishTime(matcher.group(7) + "-" + matcher.group(8) + "-" + matcher.group(9));
//                        policy.setPolicyContent("");
//
//                        try {
//                            String policyHtml = GetHtml.getHtml(policyUrl);
//                            Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                            while (matcher1.find()) {
//                                String text = matcher1.group(1).replaceAll("<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
//                                policy.setPolicyContent(text.trim());
//                            }
//                            InfoSave.insert(policy);
//                        } catch (Exception e) {
//                            System.out.println("Sorry, Page Not Found");
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    public static void mof(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        String content = "<div class=\"*TRS_Editor\"*>([\\s\\S]*?)</div>";
//        int pages = 23;
//        for (int i = 1; i <= pages; i++) {
//            String realHtml;
//            if (i == 1) {
//                realHtml = GetHtml.getHtml3("http://www.mof.gov.cn/zhengwuxinxi/zhengcefabu/index.htm");
//            } else {
//                realHtml = GetHtml.getHtml3(String.format("http://www.mof.gov.cn/zhengwuxinxi/zhengcefabu/index_%s.htm", i - 1));
//            }
//            Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//            while (matcher.find()) {
//                if (Integer.parseInt(matcher.group(3)) > limitYear) {
//                    String policyUrl = matcher.group(1).replace("./", "http://www.mof.gov.cn/zhengwuxinxi/zhengcefabu/");
//                    System.out.println(policyUrl);
//                    System.out.println(matcher.group(2));
//
//                    policy.setPolicyUrl(policyUrl);
//                    policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", ""));
//                    policy.setPolicySource(sourceName);
//                    policy.setPolicyKeywords("");
//                    policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
//                    policy.setPolicyContent("");
//                    try {
//                        String policyHtml = GetHtml.getHtml3(policyUrl);
//                        Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                        while (matcher1.find()) {
//                            String text = matcher1.group(1).replaceAll("<style[\\s\\S]*?/style>|<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
//                            policy.setPolicyContent(text.trim());
//                        }
//                        InfoSave.insert(policy);
//                    } catch (Exception e) {
//                        System.out.println("404");
//                    }
//
//                }
//            }
//        }
//    }
//
//    public static void audit(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        int pages = 3;
//        for (int i = 1; i <= pages; i++) {
//            System.out.println("this is : " + i);
//            String realHtml;
//            realHtml = GetHtml.getHtml("http://xinxi.audit.gov.cn:8888/gdnps/searchIndex.jsp?" +
//                    "params=%257B%2522goPage%2522%253A" + String.format("%s", i) + "%252C%2522orderBy" +
//                    "%2522%253A%255B%257B%2522orderBy%2522%253A%2522orderTime%2522%252C%2522reverse%2522%253" +
//                    "Atrue%257D%255D%252C%2522pageSize%2522%253A20%252C%2522queryParam%2522%253A%255B%257B%2522" +
//                    "shortName%2522%253A%2522ztfl%2522%252C%2522value%2522%253A%2522" +
//                    "subcat%252F02%2522%257D%252C%257B%257D%252C%257B%257D%255D%257D" +
//                    "&callback=jQuery191023868963470807913_1554537117720&_=1554537117748");
//            Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//            while (matcher.find()) {
//                if (Integer.parseInt(matcher.group(3)) > limitYear) {
//                    String policyUrl = String.format("http://xinxi.audit.gov.cn:8888/gdnps/content.jsp?id=%s", matcher.group(1));
//                    System.out.println(policyUrl);
//                    System.out.println(matcher.group(2));
//
//                    policy.setPolicyUrl(policyUrl);
//                    policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;|(\\\\u\\w{4})", ""));
//                    policy.setPolicySource(sourceName);
//                    policy.setPolicyKeywords("");
//                    policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
//                    String text = matcher.group(6).replace("\n", "")
//                            .replaceAll("<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
//                    policy.setPolicyContent(text);
//                    InfoSave.insert(policy);
//                }
//            }
//        }
//    }
//
//    public static void sasac(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        String content = "<!-- 媒资管理系统专用-->([\\s\\S]*?)<!--二维码生成专用 -->";
//        int pages = 13;
//        for (int i = 1; i <= pages; i++) {
//            String realHtml;
//            if (i == 1) {
//                realHtml = GetHtml.getHtml(sourceUrl + "n2588035/n2588320/n2588335/index.html");
//            } else {
//                realHtml = GetHtml.getHtml(sourceUrl + String.format("n2588035/n2588320/n2588335/index_2603340_%s.html", i - 1));
//            }
//            Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//            while (matcher.find()) {
//                if (Integer.parseInt(matcher.group(3)) > limitYear) {
//                    String policyUrl = sourceUrl + matcher.group(1);
//                    System.out.println(policyUrl);
//                    System.out.println(matcher.group(2));
//
//                    policy.setPolicyUrl(policyUrl);
//                    policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", ""));
//                    policy.setPolicySource(sourceName);
//                    policy.setPolicyKeywords("");
//                    policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
//                    policy.setPolicyContent("");
//                    String policyHtml = GetHtml.getHtml(policyUrl);
//                    Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                    while (matcher1.find()) {
//                        String text = matcher1.group(1).replaceAll("<style[\\s\\S]*?/style>|<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
//                        policy.setPolicyContent(text.trim());
//                    }
//                    InfoSave.insert(policy);
//                }
//            }
//        }
//    }
//
//    public static void customs(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        String content = "<div class=\"easysite-news-content\">([\\s\\S]*?)</div>";
//        String[] mohrsss = {"302267", "302268", "302269"};
//
//        for(String tag : mohrsss) {
//            int pages;
//            try {
//                pages = GetPage.getCustomsPage(GetHtml.getHtml(String.format("http://www.customs.gov.cn/customs/302249/302266/%s/index.html", tag)));
//            } catch (Exception e) {
//                System.out.println("无法访问");
//                break;
//            }
//            if ("302267".equals(tag)) {
//                pages = 5;
//            }
//            for (int i = 1; i <= pages; i++) {
//                String realHtml;
//                if ("302267".equals(tag)) {
//                    realHtml = GetHtml.getHtml(String.format("http://www.customs.gov.cn/customs/302249/302266/302267/2011fa33-%s.html", i));
//                } else if("302268".equals(tag)) {
//                    realHtml = GetHtml.getHtml("http://www.customs.gov.cn/customs/302249/302266/302268/index.html");
//                } else {
//                    realHtml = GetHtml.getHtml(String.format("http://www.customs.gov.cn/eportal/ui?pageId=302269" +
//                            "&currentPage=%s&moduleId=fbabca72040440eeae48509739453d32", i));
//                }
//                Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//                while (matcher.find()) {
//                    if (Integer.parseInt(matcher.group(3)) > limitYear) {
//                        String policyUrl = "http://www.customs.gov.cn" + matcher.group(1);
//                        System.out.println(policyUrl);
//                        System.out.println(matcher.group(2));
//
//                        policy.setPolicyUrl(policyUrl);
//                        policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", ""));
//                        policy.setPolicySource(sourceName);
//                        policy.setPolicyKeywords("");
//                        policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
//                        policy.setPolicyContent("");
//                        try {
//                            String policyHtml = GetHtml.getHtml(policyUrl);
//                            Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                            while (matcher1.find()) {
//                                String text = matcher1.group(1).replaceAll("<style[\\s\\S]*?/style>|<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
//                                policy.setPolicyContent(text.trim());
//                            }
//                            InfoSave.insert(policy);
//                        } catch (Exception e) {
//                            System.out.println("multichoice");
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    public static void ggj(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        String content = "<div class=\"conbox2 boxcenter\">([\\s\\S]*?)</div>";
//        int pages = GetPage.getGgjPage(GetHtml.getHtml("http://www.ggj.gov.cn/zcfg/fgxwj/"));
//        for (int i = 1; i <= pages; i++) {
//            String realHtml;
//            if (i == 1) {
//                realHtml = GetHtml.getHtml("http://www.ggj.gov.cn/zcfg/fgxwj/index.htm");
//            } else {
//                realHtml = GetHtml.getHtml(String.format("http://www.ggj.gov.cn/zcfg/fgxwj/index_%s.htm", i - 1));
//            }
//            Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//            while (matcher.find()) {
//                if (Integer.parseInt(matcher.group(3)) > limitYear) {
//                    String policyUrl = "http://www.ggj.gov.cn/zcfg/fgxwj/" + matcher.group(1);
//                    System.out.println(policyUrl);
//                    System.out.println(matcher.group(2));
//
//                    policy.setPolicyUrl(policyUrl);
//                    policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", ""));
//                    policy.setPolicySource(sourceName);
//                    policy.setPolicyKeywords("");
//                    policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
//                    policy.setPolicyContent("");
//
//                    String policyHtml = GetHtml.getHtml(policyUrl);
//                    Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                    while (matcher1.find()) {
//                        String text = matcher1.group(1).replaceAll("<style[\\s\\S]*?/style>|<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
//                        policy.setPolicyContent(text.trim());
//                    }
//                    InfoSave.insert(policy);
//                }
//            }
//        }
//    }
//
//    public static void hmo(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        String content = "<!--FILTEREDITORCSS=\"TRUE\"-->([\\s\\S]*?)<div class=\"shareCon\">";
//        String[] mohrsss = {"zcfg_new/xf/", "zcfg_new/jbf/flwj/xg/", "zcfg_new/jbf/flwj/am/",
//                "zcfg_new/gafg/sfxz/", "zcfg_new/gafg/gfxwj/"};
//        for(String tag : mohrsss) {
//            int pages = GetPage.getMohrssPage(GetHtml.getHtml(sourceUrl + tag));
//            for (int i = 1; i <= pages; i++) {
//                String realHtml;
//                if (i == 1) {
//                    realHtml = GetHtml.getHtml(sourceUrl + tag + "index.html");
//                } else {
//                    realHtml = GetHtml.getHtml(sourceUrl + tag + String.format("index_%s.html", i - 1));
//                }
//                Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//                while (matcher.find()) {
//                    if (Integer.parseInt(matcher.group(3)) > limitYear) {
//                        String policyUrl = sourceUrl + tag + matcher.group(1);
//                        System.out.println(policyUrl);
//                        System.out.println(matcher.group(2));
//                        policy.setPolicyUrl(policyUrl);
//                        policy.setPolicyTitle(matcher.group(2));
//                        policy.setPolicySource(sourceName);
//                        policy.setPolicyKeywords("");
//                        policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
//                        policy.setPolicyContent("");
//
//                        String policyHtml = GetHtml.getHtml(policyUrl);
//                        Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                        while (matcher1.find()) {
//                            String text = matcher1.group(1).replaceAll("<style[\\s\\S]*?/style>|<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
//                            policy.setPolicyContent(text.trim());
//                        }
//                        InfoSave.insert(policy);
//                    }
//                }
//            }
//        }
//    }
//
//    public static void csrc(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        String content = "<div class=\"content\">([\\s\\S]*?)<script language=\"javascript\">";
//        String realSourceUrl = "http://www.csrc.gov.cn/pub/newsite/flb/flfg/";
//        String[] mohrsss = {
//                "xzfg_8248/", "bmgz/zhl/", "bmgz/fxl/", "bmgz/ssl/", "bmgz/scjy/", "bmgz/zjgs/", "bmgz/jjl/",
//                "bmgz/qhl/", "bmgf/zh/jgzz/", "bmgf/zh/lf/", "bmgf/zh/xzxk/", "bmgf/zh/jcth/", "bmgf/zh/dcycf/",
//                "bmgf/zh/gfxwjfxq/"
//        };
//        for(String tag : mohrsss) {
//            int pages = GetPage.getMohrssPage(GetHtml.getHtml(realSourceUrl + tag));
//            for (int i = 1; i <= pages; i++) {
//                String realHtml;
//                if (i == 1) {
//                    realHtml = GetHtml.getHtml(realSourceUrl + tag + "index.html");
//                } else {
//                    realHtml = GetHtml.getHtml(realSourceUrl + tag + String.format("index_%s.html", i - 1));
//                }
//                Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//                while (matcher.find()) {
//                    if (Integer.parseInt(matcher.group(3)) > limitYear) {
//                        String policyUrl = realSourceUrl + tag + matcher.group(1);
//                        System.out.println(policyUrl);
//                        System.out.println(matcher.group(2));
//
//                        policy.setPolicyUrl(policyUrl);
//                        policy.setPolicyTitle(matcher.group(2));
//                        policy.setPolicySource(sourceName);
//                        policy.setPolicyKeywords("");
//                        policy.setPolicyContent("");
//
//                        String policyHtml = GetHtml.getHtml(policyUrl);
//                        String dates = FindDate.findDate(policyHtml);
//                        if(!"".equals(dates) && Integer.parseInt(dates.split("-")[0]) > limitYear) {
//                            policy.setPublishTime(dates);
//                        } else {
//                            policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
//                        }
//
//                        Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                        while (matcher1.find()) {
//                            String text = matcher1.group(1).replaceAll("<style[\\s\\S]*?/style>|<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
//                            policy.setPolicyContent(text.trim());
//                        }
//                        InfoSave.insert(policy);
//                    }
//                }
//            }
//        }
//    }
//
//    public static void cbrc(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        String content = "<div class=\"timu STYLE6\">([\\s\\S]*?)</center>";
//        int pages = GetPage.getCbrcPage(GetHtml.getHtml("http://www.cbrc.gov.cn/more.do?itemUuid=800103&current=1"));
//        for (int i = 1; i <= pages; i++) {
//            String realHtml;
//            realHtml = GetHtml.getHtml(String.format("http://www.cbrc.gov.cn/more.do?itemUuid=800103&current=%s", i));
//            Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//            while (matcher.find()) {
//                if (Integer.parseInt(matcher.group(3)) > limitYear) {
//                    String policyUrl = "http://www.cbrc.gov.cn" + matcher.group(1);
//                    System.out.println(policyUrl);
//                    System.out.println(matcher.group(2).trim());
//
//                    policy.setPolicyUrl(policyUrl);
//                    policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", "").trim());
//                    policy.setPolicySource(sourceName);
//                    policy.setPolicyKeywords("");
//                    policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
//                    policy.setPolicyContent("");
//
//                    String policyHtml = GetHtml.getHtml(policyUrl);
//                    Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                    while (matcher1.find()) {
//                        String text = matcher1.group(1).replaceAll("<style[\\s\\S]*?/style>|<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
//                        policy.setPolicyContent(text.trim());
//                    }
//                    InfoSave.insert(policy);
//                }
//            }
//        }
//    }
//
//    public static void caac(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        String content = "<div class=\"content\" data-role=\"n_content\" >([\\s\\S]*?)</div>";
//        try {
//            String pages = GetPage.getPage(GetHtml.getHtml("http://www.caac.gov.cn/was5/web/search?page=1&" +
//                    "channelid=211383&was_custom_expr=+PARENTID%3D%2710%27+or+CLASSINFOID%3D%2710%27+" +
//                    "&perpage=20&outlinepage=7&orderby=-fabuDate%2C-DOC_ID&selST=All&fl=10"));
//            for (int i = 1; i <= Integer.parseInt(pages); i++) {
//                String realHtml;
//                realHtml = GetHtml.getHtml("http://www.caac.gov.cn/was5/web/search?" + String.format("page=%s", i) +
//                        "&channelid=211383&was_custom_expr=+PARENTID%3D%2710%27+or+CLASSINFOID%3D%2710%27+&perpage=20" +
//                        "&outlinepage=7&orderby=-fabuDate%2C-DOC_ID&selST=All&fl=10");
//                Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//                while (matcher.find()) {
//                    if (Integer.parseInt(matcher.group(3)) > limitYear) {
//                        String policyUrl = matcher.group(1);
//                        System.out.println(policyUrl);
//                        System.out.println(matcher.group(2).trim());
//
//                        policy.setPolicyUrl(policyUrl);
//                        policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", "").trim());
//                        policy.setPolicySource(sourceName);
//                        policy.setPolicyKeywords("");
//                        policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
//                        policy.setPolicyContent("");
//
//                        String policyHtml = GetHtml.getHtml(policyUrl);
//                        Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                        while (matcher1.find()) {
//                            String text = matcher1.group(1).replaceAll("<style[\\s\\S]*?/style>|<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
//                            policy.setPolicyContent(text.trim());
//                        }
//                        InfoSave.insert(policy);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//
//    public static void nra(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        String content = "<div class=TRS_Editor>([\\s\\S]*?)<script";
//        String realSourceUrl = sourceUrl + "jgzf/";
//        String[] mohrsss = {
//                "sbjg/zcfgg/", "gcjg/gcjgjgyj/", "yxjg/zcfg/", "aqjc/zcfg/"
//        };
//        for(String tag : mohrsss) {
//            int pages = GetPage.getMohrssPage(GetHtml.getHtml(realSourceUrl + tag));
//            for (int i = 1; i <= pages; i++) {
//                String realHtml;
//                if (i == 1) {
//                    realHtml = GetHtml.getHtml(realSourceUrl + tag + "index.shtml");
//                } else {
//                    realHtml = GetHtml.getHtml(realSourceUrl + tag + String.format("index_%s.shtml", i - 1));
//                }
//                Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//                while (matcher.find()) {
//                    if (Integer.parseInt(matcher.group(1)) > limitYear) {
//                        String partUrl = matcher.group(4).replace("../", "").replace("./", "");
//                        String policyUrl;
//                        if (partUrl.contains("flfg")) {
//                            policyUrl = realSourceUrl + partUrl;
//                        } else {
//                            policyUrl = realSourceUrl + tag + partUrl;
//                        }
//                        System.out.println(policyUrl);
//                        System.out.println(matcher.group(5));
//
//                        policy.setPolicyUrl(policyUrl);
//                        policy.setPolicyTitle(matcher.group(5));
//                        policy.setPolicySource(sourceName);
//                        policy.setPolicyKeywords("");
//                        policy.setPublishTime(matcher.group(1) + "-" + matcher.group(2) + "-" + matcher.group(3));
//                        policy.setPolicyContent("");
//
//                        if(!policyUrl.contains(".pdf") && !policyUrl.contains(".doc")) {
//                            String policyHtml = GetHtml.getHtml(policyUrl);
//                            Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                            while (matcher1.find()) {
//                                String text = matcher1.group(1).replaceAll("<.*?>|&.*?;|\\s", "");
//                                policy.setPolicyContent(text.trim());
//                            }
//                        }
//                        InfoSave.insert(policy);
//                    }
//                }
//            }
//        }
//    }
//
//    public static void spb(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        String content = "<div class=\"content\">([\\s\\S]*?)<div style=\"display:none;\">";
//        String realSourceUrl = sourceUrl + "zc/";
//        String[] mohrsss = {
//                "ghjbz_1/", "flfgjzc_1/"
//        };
//        for(String tag : mohrsss) {
//            int pages = GetPage.getMohrssPage(GetHtml.getHtml(realSourceUrl + tag));
//            for (int i = 1; i <= pages; i++) {
//                String realHtml;
//                if (i == 1) {
//                    realHtml = GetHtml.getHtml(realSourceUrl + tag + "index.html");
//                } else {
//                    realHtml = GetHtml.getHtml(realSourceUrl + tag + String.format("index_%s.html", i - 1));
//                }
//                Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//                while (matcher.find()) {
//                    if (Integer.parseInt(matcher.group(1)) > limitYear) {
//                        String partUrl = matcher.group(4).replace("../", "").replace("./", "");
//                        String policyUrl;
//                        if (matcher.group(4).contains("./2")) {
//                            policyUrl = realSourceUrl + tag + partUrl;
//                        } else if (matcher.group(4).contains("../../")) {
//                            policyUrl = sourceUrl + partUrl;
//                        } else if (matcher.group(4).contains("http")) {
//                            continue;
//                        } else {
//                            policyUrl = realSourceUrl + partUrl;
//                        }
//                        System.out.println(policyUrl);
//                        System.out.println(matcher.group(5));
//
//                        policy.setPolicyUrl(policyUrl);
//                        policy.setPolicyTitle(matcher.group(5));
//                        policy.setPolicySource(sourceName);
//                        policy.setPolicyKeywords("");
//                        policy.setPublishTime(matcher.group(1) + "-" + matcher.group(2) + "-" + matcher.group(3));
//                        policy.setPolicyContent("");
//                        try {
//                            String policyHtml = GetHtml.getHtml(policyUrl);
//                            Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                            while (matcher1.find()) {
//                                String text = matcher1.group(1).replaceAll("<style[\\s\\S]*?/style>|<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
//                                policy.setPolicyContent(text.trim());
//                            }
//                            InfoSave.insert(policy);
//                        } catch (Exception e) {
//                            System.out.println("403");
//                        }
//                        try {
//                            TimeUnit.SECONDS.sleep(1);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//    }

    public static void main(String[] args) {
        getSourceLists();
    }
}
