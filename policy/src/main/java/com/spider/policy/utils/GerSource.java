package com.spider.policy.utils;

import com.spider.policy.bases.GetHtml;
import com.spider.policy.bases.GetPage;
import com.spider.policy.entity.Policy;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.spider.policy.bases.GetProxy.getRandomProxy;

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
//        if("国家市场监督管理总局".equals(sourceName)) {
//            samrsaic(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("国家税务总局".equals(sourceName)) {
//            chinatax(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("国家统计局".equals(sourceName)) {
//            stats(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("中华人民共和国国家发展和改革委员会".equals(sourceName)) {
//            ndrc(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("中华人民共和国司法部".equals(sourceName)) {
//            moj(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("中华人民共和国人力资源和社会保障部".equals(sourceName)) {
//            mohrss(policy, sourceUrl, sourceName, sourceRule);
//        }
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
        int pages = 16;
        String content = "<div class=\"Three_xilan_02\">([\\s\\S]*?)</div>";
        for(int i = 1; i <= pages; i++) {
            String realHtml;
            if(i == 1) {
                realHtml = GetHtml.getHtml("http://gkml.samr.gov.cn/2140/2170/list.html");
            } else {
                realHtml = GetHtml.getHtml(String.format("http://gkml.samr.gov.cn/2140/2170/list_%s.html", i - 1));
            }
            Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);

            while (matcher.find()) {
                if(Integer.parseInt(matcher.group(3)) > limitYear) {
                    String policyUrl = "http://gkml.samr.gov.cn" + matcher.group(1);
                    System.out.println(policyUrl);
                    System.out.println(matcher.group(2));

                    policy.setPolicyUrl(policyUrl);
                    policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", ""));
                    policy.setPolicySource(sourceName);
                    policy.setPolicyKeywords("");
                    policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
                    policy.setPolicyContent("");
                    String policyHtml = GetHtml.getHtml(policyUrl);
                    Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
                    while (matcher1.find()) {
                        String text = matcher1.group(1).replaceAll("<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
                        policy.setPolicyContent(text.trim());
                    }
                    VedioInfoSave.insert(policy);
                }
            }
        }
    }

    // 封IP
    public static void chinatax(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
        int pages = 49;
        String content = "<li class=\"sv_texth3\" id=\"tax_content\">([\\s\\S]*?)</li>";
        String time = "国家税务总局<br[\\s\\S]*?(\\d+)年(\\d+)月(\\d+)日";
        for(int i = 1; i <= pages; i++) {
            String realHtml;
            if(i == 1) {
                realHtml = GetHtml.getHtml(sourceUrl + "n810341/n810755/index.html");
                System.out.println(realHtml);
            } else {
                realHtml = GetHtml.getHtml(sourceUrl + String.format("n810341/n810755/index_3849171_%s.html", i - 1));
            }
            Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);

            while (matcher.find()) {
                String policyUrl = "http://www.chinatax.gov.cn" + matcher.group(1);
                System.out.println(policyUrl);
                System.out.println(matcher.group(2));

                policy.setPolicyUrl(policyUrl);
                policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", ""));
                policy.setPolicySource(sourceName);
                policy.setPolicyKeywords("");

                policy.setPolicyContent("");
                String policyHtml = GetHtml.getHtml(policyUrl);
                Matcher matcher1 = Pattern.compile(time).matcher(policyHtml);
                while (matcher1.find()) {
                    String times = matcher1.group(1) + "-" + matcher1.group(2) + "-" + matcher1.group(3);
                    policy.setPublishTime(times.trim());
                }
                Matcher matcher2 = Pattern.compile(content).matcher(policyHtml);
                while (matcher2.find()) {
                    String text = matcher2.group(1).replaceAll("<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
                    policy.setPolicyContent(text.trim());
                }
//                VedioInfoSave.insert(policy);
                System.out.println(policy);
            }
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void stats(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
        int pages = 5;
        String content = "<div class=\"content\".*?>[\\s\\S]*?<span.*?>([\\s\\S]*?)</div>";
        String time = "（.*?<span lang=\"EN-US\">(\\d{4}).*?年<span lang=\"EN-US\">(\\d+).*?月<span lang=\"EN-US\">(\\d+).*?日.*?）";
        for(int i = 1; i <= pages; i++) {
            String realHtml;
            if(i == 1) {
                realHtml = GetHtml.getHtml("http://www.stats.gov.cn/statsinfo/32/42/48/list.htm");
            } else if(i == 2) {
                realHtml = GetHtml.getHtml("http://www.stats.gov.cn/statsinfo/32/42/48/list_1.htm");
            } else {
                realHtml = GetHtml.getHtml(String.format("http://www.stats.gov.cn/statsinfo/32/42/4%s/list.htm", i));
            }
            if(i > 2) {
                Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
                while (matcher.find()) {
                    if(Integer.parseInt(matcher.group(3)) > limitYear) {
                        String policyUrl = "http://www.stats.gov.cn/statsinfo" + matcher.group(1);
                        System.out.println(policyUrl);
                        System.out.println(matcher.group(2));

                        policy.setPolicyUrl(policyUrl);
                        policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", ""));
                        policy.setPolicySource(sourceName);
                        policy.setPolicyKeywords("");
                        policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
                        policy.setPolicyContent("");
                        String policyHtml = GetHtml.getHtml(policyUrl);
                        Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
                        while (matcher1.find()) {
                            String text = matcher1.group(1).replaceAll("<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->|<style type=\"text/css\">([\\s\\S]*?)</style>", "");
                            policy.setPolicyContent(text.trim());
                        }
                        VedioInfoSave.insert(policy);
                    }
                }
            }
            if(i <= 2) {
                String rule = "<li class=\"mc\">[\\s\\S]*?<a href=\"../../..(.*?)\" target=\"_blank\".*?>(.*?)</a>";
                Matcher matcher = Pattern.compile(rule).matcher(realHtml);
                while (matcher.find()) {
                    String policyUrl = "http://www.stats.gov.cn/statsinfo" + matcher.group(1);
                    System.out.println(policyUrl);
                    System.out.println(matcher.group(2));

                    policy.setPolicyUrl(policyUrl);
                    policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", ""));
                    policy.setPolicySource(sourceName);
                    policy.setPolicyKeywords("");

                    policy.setPolicyContent("");
                    String policyHtml = GetHtml.getHtml(policyUrl);
                    Matcher matcher2 = Pattern.compile(time).matcher(policyHtml);
                    while (matcher2.find()) {
                        String times = matcher2.group(1) + "-" + matcher2.group(2) + "-" + matcher2.group(3);
                        policy.setPublishTime(times.trim());
                    }
                    Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
                    while (matcher1.find()) {
                        String text = matcher1.group(1).replaceAll("<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->|<style type=\"text/css\">([\\s\\S]*?)</style>", "");
                        policy.setPolicyContent(text.trim());
                    }
                    VedioInfoSave.insert(policy);
                }
            }
        }
    }

    public static void ndrc(Policy policy, String sourceUrl, String sourceName, String sourceRule) {

        String content = "<div class=\"txt1\" id=\"zoom\">([\\s\\S]*)<ul class=\"txt_bottom clearfix\">";
        String[] ndrcs = {"zcfbl", "gfxwj", "zcfbgg", "zcfbghwb", "zcfbtz", "zcfbqt"};
        for(String tag : ndrcs) {
            int pages = GetPage.getNdrcPage(GetHtml.getHtml(String.format("http://www.ndrc.gov.cn/zcfb/%s/index.html", tag)));
            for(int i = 1; i <= pages; i++) {
                String realHtml;
                if(i == 1) {
                    realHtml = GetHtml.getHtml(sourceUrl + String.format("zcfb/%s/index.html", tag));
                } else {
                    realHtml = GetHtml.getHtml(sourceUrl + String.format("zcfb/%s/index_%s.html", tag, i - 1));
                }
                Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);

                while (matcher.find()) {
                    if(Integer.parseInt(matcher.group(1)) > limitYear) {
                        String policyUrl = String.format("http://www.ndrc.gov.cn/zcfb/%s", tag) + matcher.group(4);
                        System.out.println(policyUrl);
                        System.out.println(matcher.group(5));

                        String policyHtml = GetHtml.getHtml(policyUrl);
                        Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
                        while (matcher1.find()) {
                            // <(?!img|iframe).*?>
                            String text = matcher1.group(1).replaceAll("<.*?>|&nbsp;|currentpage=1;", "");
                            policy.setPolicyContent(text.trim());
                        }
                        policy.setPolicyUrl(policyUrl);
                        policy.setPolicyTitle(matcher.group(5).replaceAll("&.*?;", ""));
                        policy.setPolicySource(sourceName);
                        policy.setPolicyKeywords("");
                        policy.setPublishTime(matcher.group(1) + "-" + matcher.group(2) + "-" + matcher.group(3));
                        VedioInfoSave.insert(policy);
                    }
                }
            }
        }
    }

    public static void moj(Policy policy, String sourceUrl, String sourceName, String sourceRule) {

        String content = "<div class=\"con\" id=\"content\">([\\s\\S]*)</div> ";
        String[] mojs = {"tzwj", "595", "594"};
        for(String tag : mojs) {
            String realHtml = GetHtml.getHtml(sourceUrl + String.format("json/%s_1.json", tag));
            Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);

            while (matcher.find()) {
                if(Integer.parseInt(matcher.group(3)) > limitYear) {
                    String policyUrl = "http://www.moj.gov.cn" + matcher.group(1).replace("http://www.moj.gov.cn", "").trim();
                    System.out.println(policyUrl);
                    System.out.println(matcher.group(2));

                    String policyHtml = GetHtml.getHtml(policyUrl);
                    Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
                    while (matcher1.find()) {
                        // <(?!img|iframe).*?>
                        String text = matcher1.group(1).replaceAll("<style.*?/script>|<.*?>|&nbsp;|currentpage=1;", "");
                        policy.setPolicyContent(text.trim());
                    }
                    policy.setPolicyUrl(policyUrl);
                    policy.setPolicyTitle(matcher.group(2)
                            .replaceAll("&.*?;|<.*?>", "")
                            .replace("\\u200B", "").replace("\\u200D", "")
                            .replace("\\u201C", "“")
                            .replace("\\u201D", "“")
                            .replace("\\u2014", "-"));
                    policy.setPolicySource(sourceName);
                    policy.setPolicyKeywords("");
                    policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
                    VedioInfoSave.insert(policy);
                }
            }
        }
    }

    public static void mohrss(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
        String content = "<div class=\"insMainConTxt_a\">([\\s\\S]*?)<script";
        String[] mohrsss = {"bmgz", "gfxwj"};
        for(String tag : mohrsss) {
            int pages = GetPage.getMohrssPage(GetHtml.getHtml(String.format("http://www.mohrss.gov.cn/gkml/zcfg/%s/", tag)));
            for (int i = 1; i <= pages; i++) {
                String realHtml;
                if (i == 1) {
                    realHtml = GetHtml.getHtml(String.format("http://www.mohrss.gov.cn/gkml/zcfg/%s/index.html", tag));
                } else {
                    realHtml = GetHtml.getHtml(String.format("http://www.mohrss.gov.cn/gkml/zcfg/%s/index_%s.html", tag, i - 1));
                }
                Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);

                while (matcher.find()) {
                    if (Integer.parseInt(matcher.group(2)) > limitYear) {
                        String policyUrl;
                        String partUrl = matcher.group(1)
                                .replace("../", "")
                                .replace("./", "");
                        if (partUrl.contains("SYrlzyhshbzb")) {
                            policyUrl = "http://www.mohrss.gov.cn/" + partUrl;
                        } else {
                            policyUrl = String.format("http://www.mohrss.gov.cn/gkml/zcfg/%s/", tag) + partUrl;
                        }

                        System.out.println(policyUrl);
                        System.out.println(matcher.group(5));

                        policy.setPolicyUrl(policyUrl);
                        policy.setPolicyTitle(matcher.group(5).replaceAll("&.*?;", ""));
                        policy.setPolicySource(sourceName);
                        policy.setPolicyKeywords("");
                        policy.setPublishTime(matcher.group(2) + "-" + matcher.group(3) + "-" + matcher.group(4));
                        policy.setPolicyContent("");
                        String policyHtml = GetHtml.getHtml(policyUrl);
                        Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
                        while (matcher1.find()) {
                            String text = matcher1.group(1).replaceAll("<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
                            policy.setPolicyContent(text.trim());
                        }
                        VedioInfoSave.insert(policy);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        getSourceUrls();
    }
}
