package com.spider.policy.utils;

/**
 * @PackageName com.spider.policy.utils
 * @Author joel
 * @Date 2019/4/3 9:52
 **/
public class Rules {
    public static String getRule(String sourceName) {
        String sourceRule = "";

        if("中华人民共和国商务部".equals(sourceName)) {
            sourceRule = "<a title=\"(.*?)\" target=\"_blank\" href=\"(.*?)\">.*?</a><span>(\\d+)-(\\d+)-(\\d+).*?</span>\n" +
                    "</li><li>";
        }
        if("中华人民共和国外交部".equals(sourceName)) {
            sourceRule = "<li><a href=\"\\.(.*?)\" target=\"_blank\">(.*?)</a>\\((\\d+)-(\\d+)-(\\d+)\\)</li>";
        }
        if("国家市场监督管理总局".equals(sourceName)) {
            sourceRule = "<li class=\"nav04Left02_content\"><a href=\"(.*?)\" target=\"_blank\">(.*?)</a></li>[\\s\\S]*" +
                    "<li class=\"nav04Left02_contenttime\">(\\d+)-(\\d+)-(\\d+)</li>";
        }

        return sourceRule;
    }
}
