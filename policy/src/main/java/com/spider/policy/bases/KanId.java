package com.spider.policy.bases;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @PackageName com.spider.kan360.videoinfo
 * @Author joel
 * @Date 2019/1/30 16:40
 **/

public class KanId{
    public static HashSet<String> idUrl = new HashSet<>();

    public static HashSet<String> getIdList(String url) {
        String html = GetHtml.getHtml(url);
        String page = GetPage.getPage(html);

        for (int i = 1; i <= Integer.parseInt(page); i++) {
            System.out.println(i);
            System.out.println(url + String.format("&pageno=%d", i));
            String response = GetHtml.getHtml(url + String.format("&pageno=%d", i));

            String getId = "<a class=\"js-tongjic\" href=\"/(.*?)\">";
            Pattern pattern = Pattern.compile(getId);
            Matcher matcher = pattern.matcher(response);

            while (matcher.find()) {
                String vedioUrl = "https://www.360kan.com/" + matcher.group(1);
                System.out.println(vedioUrl);

                idUrl.add(vedioUrl);
            }
        }
        return idUrl;
    }
}
