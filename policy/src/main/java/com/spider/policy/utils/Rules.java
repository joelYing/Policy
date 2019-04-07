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
            sourceRule = "<li class=\"fl w100\".*?\"><a href=\"(.*?)\".*?title=\"([\\s\\S]*?)\".*?\">[\\s\\S]*?" +
                    "</a><span class=\"fr\">[\\s\\S]*?(\\d{4})年(\\d+)月(\\d+)日</span></li>";
        }
        if("中华人民共和国工业和信息化部".equals(sourceName)) {
//            sourceRule = "\"ownSubjectDn\":\"\\\\/1\\\\/29\\\\/(\\d+)\\\\/(\\d+)\\\\/(\\d+)\\\\/(\\d+)\"" +
//                    "[\\s\\S]*?\"id\":\"(\\d+)\",\"title\":\"(.*?)\"[\\s\\S]*?\"scrq\":\"(\\d{4})-(\\d+)-(\\d+)\"";
            // 112页 之后的规则
            sourceRule = "\"id\":\"(\\d+)\"[\\s\\S]*?\"title\":\"(.*?)\"[\\s\\S]*?\"[\\s\\S]*?" +
                    "publishTime\":\"(\\d{4})(\\d{2})(\\d{2})\\d+\"," +
                    "\"ownSubjectDn\":\"\\\\/1\\\\/29\\\\/(\\d+)\\\\/(\\d+)\\\\/(\\d+)\\\\/(\\d+)\"";
        }
        if("中华人民共和国财政部".equals(sourceName)) {
            sourceRule = "<td class=\"ZITI\" title=\".*?\">[\\s\\S]*?<a href=\"(.*?)\">(.*?)</a>" +
                    "[\\s\\S]*?(\\d{4})-(\\d+)-(\\d+)[\\s\\S]*?</td>";
        }
        if("中华人民共和国审计署".equals(sourceName)) {
            sourceRule = "\"id\":\"(\\d+)\"[\\s\\S]*?\"title\":\"(.*?)\"[\\s\\S]*?" +
                    "\"scrq\":\"(\\d{4})-(\\d{2})-(\\d{2})\"[\\s\\S]*?\"htmlContent\":\"([\\s\\S]*?)\"}";
        }
        if("国务院国有资产监督管理委员会".equals(sourceName)) {
            sourceRule = "<li>[\\s\\S]*?<a href=\"../../../(.*?)\" target=\"_blank\" title=\"(.*?)\">[\\s\\S]*?</a>" +
                    "<span>\\[(\\d{4})-(\\d+)-(\\d+)\\]</span></li>";
        }
        if("中华人民共和国海关总署".equals(sourceName)) {
            sourceRule = "<li><a href=\"(.*?)\"[\\s\\S]*?title=\"(.*?)\">[\\s\\S]*?</a><span>(\\d{4})-(\\d+)-(\\d+)</span></li>";
        }
        if("国家机关事务管理局".equals(sourceName)) {
            sourceRule = "<dt>[\\s\\S]*?<a href='./(.*?)' target='_blank'><script>showTitle\\('([\\s\\S]*?)'\\);" +
                    "</script></a><i>(\\d{4})-(\\d+)-(\\d+)</i></dt>";
        }
        if("国务院港澳事务办公室".equals(sourceName)) {
            sourceRule = "<li><a href=\"./(.*?)\".*?title=\"([\\s\\S]*?)\">[\\s\\S]*?</a><span>(\\d{4})-(\\d+)-(\\d+)</span></li>";
        }
        if("中国证券监督管理委员会".equals(sourceName)) {
            sourceRule = "<li>[\\s\\S]*?<a href=\"./(.*?)\".*?title=\"(.*?)\">[\\s\\S]*?</a>[\\s\\S]*?<span>(\\d{4})-(\\d+)-(\\d+)</span>";
        }
        if("中国银行保险监督管理委员会".equals(sourceName)) {
            sourceRule = "<div class=\"maincontent\">[\\s\\S]*?<a href='(.*?)' target='_bank'[\\s\\S]*?>" +
                    "<span[\\s\\S]*?title=\"[\\s\\S]*?\">([\\s\\S]*?)</span>[\\s\\S]*?<li style=\".*?\">" +
                    "[\\s\\S]*?(\\d{4})-(\\d+)-(\\d+)[\\s\\S]*?</li>[\\s\\S]*?</div>";
        }
        if("中国民用航空局".equals(sourceName)) {
            sourceRule = "<tr>[\\s\\S]*?<a href=\"(.*?)\".*?name=\"(.*?)\">[\\s\\S]*?</a>[\\s\\S]*?" +
                    "<td style=\"width:10%\" class=\"tdRQ\">[\\s\\S]*?(\\d{4})年(\\d+)月(\\d+)日[\\s\\S]*?</td>[\\s\\S]*?</tr>";
        }
        if("国家铁路局".equals(sourceName)) {
            sourceRule = "<li><span>(\\d{4})-(\\d+)-(\\d+)</span><a href='(.*?)'.*?title='(.*?)'>[\\s\\S]*?</a></li>";
        }
        if("国家邮政局".equals(sourceName)) {
            sourceRule = "<li><span.*?>.*?(\\d{4})-(\\d+)-(\\d+).*?</span>[\\s\\S]*?<a href=\"(.*?)\".*?" +
                    "title=\"(.*?)\" postcnx>[\\s\\S]*?</a>[\\s\\S]*?</li>";
        }


        return sourceRule;
    }
}
