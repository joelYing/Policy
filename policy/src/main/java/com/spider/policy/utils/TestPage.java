package com.spider.policy.utils;

import com.github.suosi.commons.helper.Static;
import com.github.suosi.commons.helper.utils.Strtotime;
import com.github.suosi.commons.spider.extract.site.PageExtract;
import com.github.suosi.commons.spider.extract.site.meta.Page;
import com.github.suosi.commons.spider.utils.UrlUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestPage {
    public static void main(String[] args) {
        Page page = PageExtract.url("http://www.spb.gov.cn/zc/flfgjzc_1/index.html");
        if (page != null) {
            Set<String> links = page.getLinks();
            if (links != null) {
                for (String link : links) {
                    if (UrlUtils.guessArticleUrl(link, null) || UrlUtils.guessListUrl(link, null)) {
                        String regular = "http://www.spb.gov.cn/\\w*?/\\w*?_\\d+/\\d{6}/t\\d{8}_\\d+.html";
//                        String regular = link;
                        Matcher matcher = Pattern.compile(regular).matcher(link);
                        while (matcher.find()) {
                            String realLink = matcher.group();
                            System.out.println("A -> " + realLink);
                        } // http://www.spb.gov.cn/\w*?/\w*?_\d+/\d{6}/t\d{8}_\d+.html
                    }
                }
            }
        }

    }

}
