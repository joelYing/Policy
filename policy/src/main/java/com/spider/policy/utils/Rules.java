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
            sourceRule = "<li class=\"mc\">[\\s\\S]*?<a href=\"../..(.*?)\" target=\"_blank\">(.*?)</a>" +
                    "[\\s\\S]*?<li class=\"fbrq\" title=\".*?\">(\\d+)年(\\d+)月(\\d+)日</li>";
        }
        if("国家税务总局".equals(sourceName)) {
            sourceRule = "<dd><a href=\"../..(.*?)\" target=_blank title=\"(.*?)\"><span style=\".*?\">.*?</span>.*?</a></dd>";
        }
        if("国家统计局".equals(sourceName)) {
            sourceRule = "<li class=\"mc\">[\\s\\S]*?<a href=\"../../..(.*?)\" target=\"_blank\" .*?\">(.*?)</a>" +
                    "[\\s\\S]*?<li class=\"fbrq\" title=\".*?\">(\\d+)年(\\d+)月(\\d+)日</li>";
        }
        if("中华人民共和国国家发展和改革委员会".equals(sourceName)) {
            sourceRule = "<li  class=\"li\"><font class=\"date\">(\\d{4})/(\\d+)/(\\d+)</font><a href=\".(.*?)\" " +
                    "target=\"_blank\">(.*?)</a><span class=\"new\"></span></li>";
        }
        if("中华人民共和国司法部".equals(sourceName)) {
            sourceRule = "\"infostaticurl\":\"(.*?)\",\"listtitle\":\"(.*?)\",\"releasedate\":\"(\\d{4})-(\\d+)-(\\d+)\"";
        }
        if("中华人民共和国人力资源和社会保障部".equals(sourceName)) {
            sourceRule = "<li class=\"mc\">[\\s\\S]*?<a href=\"(.*?)\" target=\"_blank\".*?><font[\\s\\S]*?>.*?</font></a>" +
                    "[\\s\\S]*?<li class=\"fbrq\".*?title=\"(\\d{4})年(\\d+)月(\\d+)日\">[\\s\\S]*?<td width=\"420px\" " +
                    "colspan=\"3\">(.*?)</td>";
        }
        if("中华人民共和国交通运输部".equals(sourceName)) {
            sourceRule = "";
        }
        if("中华人民共和国工业和信息化部".equals(sourceName)) {
            sourceRule = "a";
        }
        if("中华人民共和国财政部".equals(sourceName)) {
            sourceRule = "a";
        }
        if("中华人民共和国审计署".equals(sourceName)) {
            sourceRule = "a";
        }
        if("国务院国有资产监督管理委员会".equals(sourceName)) {
            sourceRule = "a";
        }
        if("中华人民共和国海关总署".equals(sourceName)) {
            sourceRule = "a";
        }
        if("国家机关事务管理局".equals(sourceName)) {
            sourceRule = "a";
        }
        if("国务院港澳事务办公室".equals(sourceName)) {
            sourceRule = "a";
        }
        if("中国证券监督管理委员会".equals(sourceName)) {
            sourceRule = "a";
        }
        if("中国银行保险监督管理委员会".equals(sourceName)) {
            sourceRule = "a";
        }
        if("中国民用航空局".equals(sourceName)) {
            sourceRule = "a";
        }
        if("国家铁路局".equals(sourceName)) {
            sourceRule = "a";
        }
        if("国家邮政局".equals(sourceName)) {
            sourceRule = "a";
        }
        if("中华人民共和国交通运输部".equals(sourceName)) {
            sourceRule = "a";
        }


        return sourceRule;
    }
}
