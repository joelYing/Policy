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

    public static int getNdrcPage(String response) {
        String page = "1";
        String pages = "createPageHTML\\((\\d+), \\d+, \"index\", \"html\"\\);";
        Pattern pattern = Pattern.compile(pages);
        Matcher matcher = pattern.matcher(response);

        // 正则提取基本视频信息
        while (matcher.find()) {
            page = matcher.group(1);
        }
        // 总页数
        System.out.println(page);

        return Integer.parseInt(page);
    }

    public static int getMohrssPage(String response) {
        String page = "1";
        String pages = "var countPage = (\\d+)//共多少页";
        Pattern pattern = Pattern.compile(pages);
        Matcher matcher = pattern.matcher(response);

        // 正则提取基本视频信息
        while (matcher.find()) {
            page = matcher.group(1);
        }
        // 总页数
        System.out.println(page);

        return Integer.parseInt(page);
    }

    public static int getCustomsPage(String response) {
        String page = "1";
        String pages = "totalpage=\"(\\d+)\"";
        Pattern pattern = Pattern.compile(pages);
        Matcher matcher = pattern.matcher(response);

        // 正则提取基本视频信息
        while (matcher.find()) {
            page = matcher.group(1);
        }
        // 总页数
        System.out.println(page);

        return Integer.parseInt(page);
    }

    public static int getGgjPage(String response) {
        String page = "1";
        String pages = "createPageHTML\\(\"439\",(\\d+), 0, \"index\", \"htm\"\\);";
        Pattern pattern = Pattern.compile(pages);
        Matcher matcher = pattern.matcher(response);

        // 正则提取基本视频信息
        while (matcher.find()) {
            page = matcher.group(1);
        }
        // 总页数
        System.out.println(page);

        return Integer.parseInt(page);
    }

    public static int getCbrcPage(String response) {
        String page = "1";
        String pages = "下页</a> <a target\"_self\" href=\"(\\d+)\\.html\">末页</a>";
        Pattern pattern = Pattern.compile(pages);
        Matcher matcher = pattern.matcher(response);

        // 正则提取基本视频信息
        while (matcher.find()) {
            page = matcher.group(1);
        }
        // 总页数
        System.out.println(page);

        return Integer.parseInt(page);
    }

}
