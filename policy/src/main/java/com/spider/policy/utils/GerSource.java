package com.spider.policy.utils;

import com.spider.policy.bases.GetHtml;
import com.spider.policy.bases.GetPage;
import com.spider.policy.entity.Policy;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @PackageName com.spider.kan360.utils
 * @Author joel
 * @Date 2019/4/2 16:23
 **/
public class GerSource {
    public static String baseUrl = "http://sousuo.gov.cn/column/30469/%d.htm";

    public static void getSourceUrls() {
        HashSet<String> selectSource = VedioInfoSave.selectSource();
        for(String sourceurl : selectSource) {
            System.out.println(sourceurl);
            getSourcePolicy(sourceurl);
        }
    }

    public static void getSourcePolicy(String sourceurl) {
        Policy policy = new Policy();
        String urls = "<li>[\\s\\S]*?<h4>[\\s\\S]*?<a href=\"(.*?)\".*?>(.*?)</a>" +
                "[\\s\\S]*?<span class=\"date\">(.*?)</span>[\\s\\S]*?</h4></li>";

        String html = GetHtml.getHtml(sourceurl);
        String page = GetPage.getPage(html);
        for(int i = 0; i <= Integer.parseInt(page); i++) {
            String realHtml = GetHtml.getHtml(String.format(baseUrl, i));
            Pattern pattern = Pattern.compile(urls);
            Matcher matcher = pattern.matcher(realHtml);

            while (matcher.find()) {
                String policyUrl = matcher.group(1);
                System.out.println(policyUrl);

                policy.setPolicyUrl(policyUrl);
                policy.setPolicyTitle(matcher.group(2));
                policy.setPolicyContent("...");
                policy.setPolicySource("国务院");
                policy.setPolicyKeywords("政策");
                policy.setPublishTime(matcher.group(3));
                VedioInfoSave.insert(policy);
            }
        }
    }

    public static void main(String[] args) {
        getSourceUrls();
    }
}
