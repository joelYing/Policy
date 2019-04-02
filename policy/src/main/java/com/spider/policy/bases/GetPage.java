package com.spider.policy.bases;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @PackageName com.spider.kan360.videoinfo
 * @Author joel
 * @Date 2019/1/31 16:20
 **/

public class GetPage {
    public static String getPage(String response) {
        String page = "1";
        String pages = "共(\\d+)页";
        Pattern pattern = Pattern.compile(pages);
        Matcher matcher = pattern.matcher(response);

        // 正则提取基本视频信息
        while (matcher.find()) {
            page = matcher.group(1);
        }
        // 总页数
        System.out.println(page);

        return page;
    }
}
