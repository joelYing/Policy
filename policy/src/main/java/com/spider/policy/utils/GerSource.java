package com.spider.policy.utils;

import com.spider.policy.bases.GetHtml;
import com.spider.policy.bases.GetPage;
import com.spider.policy.entity.Policy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @PackageName com.spider.kan360.utils
 * @Author joel
 * @Date 2019/4/2 16:23
 **/
public class GerSource {
    private static int limitYear = 2009;

    public static void getSourceUrls() {
        ArrayList<ArrayList<String>> selectSource = VedioInfoSave.selectSource();
        for(ArrayList<String> urlname : selectSource) {
            String sourceUrl = urlname.get(0);
            String sourceName = urlname.get(1);

            String sourceRule = Rules.getRule(sourceName);
            System.out.println(sourceUrl);
            getSourcePolicy(sourceName, sourceUrl, sourceRule);
//            break;
        }
    }

    public static void getSourcePolicy(String sourceName, String sourceUrl, String sourceRule) {
        Policy policy = new Policy();
//        if("中华人民共和国商务部".equals(sourceName)) {
//            mofcom(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("中华人民共和国外交部".equals(sourceName)) {
//            fmprc(policy, sourceUrl, sourceName, sourceRule);
//        }
        if("国家市场监督管理总局".equals(sourceName)) {
            samrsaic(policy, sourceUrl, sourceName, sourceRule);
        }
    }

    public static void mofcom(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
        int pages = 50;
        String content = "<!--文章正文-->([\\s\\S]*)<div class=\"artLabel\">";
        String[] mofcoms = {"c", "fwzl", "d", "e", "f", "bf", "xxfb"};
        for(String tag : mofcoms) {
            for(int i = 1; i <= pages; i++) {
                String realHtml;
                if(i == 1) {
                    realHtml = GetHtml.getHtml(sourceUrl + String.format("article/b/%s/?", tag));
                } else {
                    realHtml = GetHtml.getHtml(sourceUrl + String.format("article/b/%s/?%s", tag, i));

                }
                Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);

                while (matcher.find()) {
                    if(Integer.parseInt(matcher.group(3)) > limitYear) {
                        String policyUrl = "http://www.mofcom.gov.cn" + matcher.group(2);
                        System.out.println(policyUrl);
                        System.out.println(matcher.group(1));

                        String policyHtml = GetHtml.getHtml(policyUrl);
                        Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
                        while (matcher1.find()) {
                            // <(?!img|iframe).*?>
                            String text = matcher1.group(1).replaceAll("<.*?>|&nbsp;|currentpage=1;", "");
                            policy.setPolicyContent(text.trim());
                        }
                        policy.setPolicyUrl(policyUrl);
                        policy.setPolicyTitle(matcher.group(1).replaceAll("&.*?;", ""));
                        policy.setPolicySource(sourceName);
                        policy.setPolicyKeywords("");
                        policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
                        VedioInfoSave.insert(policy);
                    }
                }
            }
        }
    }

    public static void fmprc(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
        int pages = 3;
        String content = "<div id=\"News_Body_Txt_A\" class=\"content\">([\\s\\S]*?)</div>";
        for(int i = 1; i <= pages; i++) {
            String realHtml;
            if(i == 1) {
                realHtml = GetHtml.getHtml(sourceUrl + "web/ziliao_674904/tytj_674911/zcwj_674915/default.shtml");
            } else {
                realHtml = GetHtml.getHtml(sourceUrl + String.format("web/ziliao_674904/tytj_674911/zcwj_674915/default_%s.shtml", i - 1));
            }
            Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);

            while (matcher.find()) {
                if(Integer.parseInt(matcher.group(3)) > limitYear) {
                    String policyUrl = "https://www.fmprc.gov.cn/web/ziliao_674904/tytj_674911/zcwj_674915" + matcher.group(1);
                    System.out.println(policyUrl);
                    System.out.println(matcher.group(2));

                    policy.setPolicyUrl(policyUrl);
                    policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", ""));
                    policy.setPolicySource(sourceName);
                    policy.setPolicyKeywords("");
                    policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
                    policy.setPolicyContent("");
                    if(!policyUrl.contains("pdf")) {
                        String policyHtml = GetHtml.getHtml(policyUrl);
                        Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
                        while (matcher1.find()) {
                            String text = matcher1.group(1).replaceAll("<.*?>|&.*?;|\\s", "");
                            policy.setPolicyContent(text.trim());
                        }
                    }

                    VedioInfoSave.insert(policy);
                }
            }
        }
    }

    public static void samrsaic(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
        int pages = 17;
        String content = "<div id=\"News_Body_Txt_A\" class=\"content\">([\\s\\S]*?)</div>";
        for(int i = 1; i <= pages; i++) {
            String realHtml;
            if(i == 1) {
                System.out.println(sourceUrl + "zw/zcfg/index.html");
                realHtml = GetHtml.getHtml2(sourceUrl + "zw/zcfg/index.html");
                System.out.println(realHtml);
            } else {
                realHtml = GetHtml.getHtml2(sourceUrl + String.format("zw/zcfg/index_%s.html", i - 1));
            }
            Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);

            while (matcher.find()) {
                if(Integer.parseInt(matcher.group(3)) > limitYear) {
                    String policyUrl = matcher.group(1);
                    System.out.println(policyUrl);
                    System.out.println(matcher.group(2));

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

//                    VedioInfoSave.insert(policy);
                }
            }
        }
    }

    public static void main(String[] args) {
        getSourceUrls();
    }
}
