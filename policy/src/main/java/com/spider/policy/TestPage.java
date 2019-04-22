package com.spider.policy;

import com.github.suosi.commons.spider.extract.site.PageExtract;
import com.github.suosi.commons.spider.extract.site.meta.Page;
import com.github.suosi.commons.spider.utils.UrlUtils;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestPage {
    public static void main(String[] args) {
        Page page = PageExtract.url("http://www.fzzx.sh.gov.cn/fzzx/ixxgkzxxxgk.html");
        if (page != null) {
            Set<String> links = page.getLinks();
            if (links != null) {
                for (String link : links) {
                    if (UrlUtils.guessArticleUrl(link, null) || UrlUtils.guessListUrl(link, null)) {
                        String regular = "http://www.fzzx.sh.gov.cn/\\w*?/\\w*?_\\w*?.html";
//                        String regular = link;
                        Matcher matcher = Pattern.compile(regular).matcher(link);
                        while (matcher.find()) {
                            String realLink = matcher.group();
                            System.out.println("A -> " + realLink);
                        } // http://www.fzzx.sh.gov.cn/\w*?/\w*?_\w*?.html
                    }
                }
            }
        }

    }

}
