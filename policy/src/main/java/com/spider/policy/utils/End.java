package com.spider.policy.utils;

public class End {
//    public static void getSourcePolicy(String sourceName, String sourceUrl, String sourceRule) {
//        Policy policy = new Policy();
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
//        if("中华人民共和国交通运输部".equals(sourceName)) {
//            mot(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("中华人民共和国工业和信息化部".equals(sourceName)) {
//            // 未结束 但只采6页
//            miit(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("中华人民共和国财政部".equals(sourceName)) {
//            mof(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("中华人民共和国审计署".equals(sourceName)) {
//            audit(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("国务院国有资产监督管理委员会".equals(sourceName)) {
//            sasac(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("中华人民共和国海关总署".equals(sourceName)) {
//            // 会有403
//            customs(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("国家机关事务管理局".equals(sourceName)) {
//            ggj(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("国务院港澳事务办公室".equals(sourceName)) {
//            hmo(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("中国证券监督管理委员会".equals(sourceName)) {
//            csrc(policy, sourceUrl, sourceName, sourceRule);
//        }
////        if("中国银行保险监督管理委员会".equals(sourceName)) {
////            cbrc(policy, sourceUrl, sourceName, sourceRule);
////        }
//        if("中国民用航空局".equals(sourceName)) {
//            caac(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("国家铁路局".equals(sourceName)) {
//            nra(policy, sourceUrl, sourceName, sourceRule);
//        }
//        if("国家邮政局".equals(sourceName)) {
//            // 会有403反爬
//            spb(policy, sourceUrl, sourceName, sourceRule);
//        }
//    }

//    // 添加cookie
//    public static void chinatax(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        int pages = 2;
//        String content = "<li class=\"sv_texth3\" id=\"tax_content\">([\\s\\S]*?)</li>";
//        String time = "税务总局[\\s\\S]*?(\\d+)年(\\d+)月(\\d+)日";
//        for(int i = 1; i <= pages; i++) {
//            String realHtml;
//            if(i == 1) {
//                realHtml = GetHtml.getHtml5(sourceUrl + "n810341/n810755/index.html");
//            } else {
//                realHtml = GetHtml.getHtml5(sourceUrl + String.format("n810341/n810755/index_3849171_%s.html", i - 1));
//            }
//            Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//            while (matcher.find()) {
//                String policyUrl = "http://www.chinatax.gov.cn" + matcher.group(1);
//                System.out.println(policyUrl);
//                System.out.println(matcher.group(2));
//
//                policy.setPolicyUrl(policyUrl);
//                policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", ""));
//                policy.setPolicySource(sourceName);
//                policy.setPolicyKeywords("");
//
//                policy.setPolicyContent("");
//                try {
//                    String policyHtml = GetHtml.getHtml5(policyUrl);
//                    Matcher matcher1 = Pattern.compile(time).matcher(policyHtml);
//                    while (matcher1.find()) {
//                        String times = matcher1.group(1) + "-" + matcher1.group(2) + "-" + matcher1.group(3);
//                        policy.setPublishTime(times.trim());
//                    }
//                    Matcher matcher2 = Pattern.compile(content).matcher(policyHtml);
//                    while (matcher2.find()) {
//                        String text = matcher2.group(1).replaceAll("<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
//                        policy.setPolicyContent(text.trim());
//                    }
//                } catch (Exception e) {
//                    System.out.println("404");
//                }
//                InfoSave.insert(policy);
//            }
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }



//    // 限制访问一定时间内的次数 七页 112页之后是2011年的部分内容，未采集
//    public static void miit(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        int pages = 6;
//        String content = "<div class=\"ccontent center\".*?>([\\s\\S]*?)</div>";
//        for (int i = 1; i <= pages; i++) {
//            if (i > 0) {
//                System.out.println("this is : " + i);
//                String realHtml;
//                realHtml = GetHtml.getHtml("http://www.miit.gov.cn/gdnps/searchIndex.jsp?" +
//                        "params=%257B%2522goPage%2522%253A" + String.format("%s", i) + "%252C%2522orderBy%2522%253A%255B%257B%2522" +
//                        "orderBy%2522%253A%2522publishTime%2522%252C%2522reverse%2522%253" +
//                        "Atrue%257D%252C%257B%2522orderBy%2522%253A%2522orderTime%2522%252C%2522reverse%2522%253" +
//                        "Atrue%257D%255D%252C%2522pageSize%2522%253A10%252C%2522" +
//                        "queryParam%2522%253A%255B%257B%257D%252C%257B%257D%252C%257B%2522" +
//                        "shortName%2522%253A%2522fbjg%2522%252C%2522" +
//                        "value%2522%253A%2522%252F1%252F29%252F1146295%252F1652858%252F1652930%2522%257D%255D%257D");
//                Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//                while (matcher.find()) {
//                    if (Integer.parseInt(matcher.group(7)) > limitYear) {
////                    if (Integer.parseInt(matcher.group(3)) > limitYear) {
//                        String policyUrl = sourceUrl + String.format("n%s/n%s/n%s/n%s/c%s/content.html",
//                                matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5));
////                                  matcher.group(6), matcher.group(7), matcher.group(8), matcher.group(9), matcher.group(1));
//
//                        System.out.println(policyUrl);
//                        System.out.println(matcher.group(6));
////                        System.out.println(matcher.group(2));
//
//                        policy.setPolicyUrl(policyUrl);
//                        policy.setPolicyTitle(matcher.group(6).replaceAll("&.*?;|(\\\\u\\w{4})", ""));
//                        policy.setPolicySource(sourceName);
//                        policy.setPolicyKeywords("");
//                        policy.setPublishTime(matcher.group(7) + "-" + matcher.group(8) + "-" + matcher.group(9));
//                        policy.setPolicyContent("");
//
//                        try {
//                            String policyHtml = GetHtml.getHtml(policyUrl);
//                            Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                            while (matcher1.find()) {
//                                String text = matcher1.group(1).replaceAll("<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
//                                policy.setPolicyContent(text.trim());
//                            }
//                            InfoSave.insert(policy);
//                        } catch (Exception e) {
//                            System.out.println("Sorry, Page Not Found");
//                        }
//                    }
//                }
//            }
//        }
//    }

//
//    public static void audit(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        int pages = 3;
//        for (int i = 1; i <= pages; i++) {
//            System.out.println("this is : " + i);
//            String realHtml;
//            realHtml = GetHtml.getHtml("http://xinxi.audit.gov.cn:8888/gdnps/searchIndex.jsp?" +
//                    "params=%257B%2522goPage%2522%253A" + String.format("%s", i) + "%252C%2522orderBy" +
//                    "%2522%253A%255B%257B%2522orderBy%2522%253A%2522orderTime%2522%252C%2522reverse%2522%253" +
//                    "Atrue%257D%255D%252C%2522pageSize%2522%253A20%252C%2522queryParam%2522%253A%255B%257B%2522" +
//                    "shortName%2522%253A%2522ztfl%2522%252C%2522value%2522%253A%2522" +
//                    "subcat%252F02%2522%257D%252C%257B%257D%252C%257B%257D%255D%257D" +
//                    "&callback=jQuery191023868963470807913_1554537117720&_=1554537117748");
//            Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//            while (matcher.find()) {
//                if (Integer.parseInt(matcher.group(3)) > limitYear) {
//                    String policyUrl = String.format("http://xinxi.audit.gov.cn:8888/gdnps/content.jsp?id=%s", matcher.group(1));
//                    System.out.println(policyUrl);
//                    System.out.println(matcher.group(2));
//
//                    policy.setPolicyUrl(policyUrl);
//                    policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;|(\\\\u\\w{4})", ""));
//                    policy.setPolicySource(sourceName);
//                    policy.setPolicyKeywords("");
//                    policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
//                    String text = matcher.group(6).replace("\n", "")
//                            .replaceAll("<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
//                    policy.setPolicyContent(text);
//                    InfoSave.insert(policy);
//                }
//            }
//        }
//    }
//
//    public static void sasac(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        String content = "<!-- 媒资管理系统专用-->([\\s\\S]*?)<!--二维码生成专用 -->";
//        int pages = 13;
//        for (int i = 1; i <= pages; i++) {
//            String realHtml;
//            if (i == 1) {
//                realHtml = GetHtml.getHtml(sourceUrl + "n2588035/n2588320/n2588335/index.html");
//            } else {
//                realHtml = GetHtml.getHtml(sourceUrl + String.format("n2588035/n2588320/n2588335/index_2603340_%s.html", i - 1));
//            }
//            Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//            while (matcher.find()) {
//                if (Integer.parseInt(matcher.group(3)) > limitYear) {
//                    String policyUrl = sourceUrl + matcher.group(1);
//                    System.out.println(policyUrl);
//                    System.out.println(matcher.group(2));
//
//                    policy.setPolicyUrl(policyUrl);
//                    policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", ""));
//                    policy.setPolicySource(sourceName);
//                    policy.setPolicyKeywords("");
//                    policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
//                    policy.setPolicyContent("");
//                    String policyHtml = GetHtml.getHtml(policyUrl);
//                    Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                    while (matcher1.find()) {
//                        String text = matcher1.group(1).replaceAll("<style[\\s\\S]*?/style>|<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
//                        policy.setPolicyContent(text.trim());
//                    }
//                    InfoSave.insert(policy);
//                }
//            }
//        }
//    }
//
//    public static void customs(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        String content = "<div class=\"easysite-news-content\">([\\s\\S]*?)</div>";
//        String[] mohrsss = {"302267", "302268", "302269"};
//
//        for(String tag : mohrsss) {
//            int pages;
//            try {
//                pages = GetPage.getCustomsPage(GetHtml.getHtml(String.format("http://www.customs.gov.cn/customs/302249/302266/%s/index.html", tag)));
//            } catch (Exception e) {
//                System.out.println("无法访问");
//                break;
//            }
//            if ("302267".equals(tag)) {
//                pages = 5;
//            }
//            for (int i = 1; i <= pages; i++) {
//                String realHtml;
//                if ("302267".equals(tag)) {
//                    realHtml = GetHtml.getHtml(String.format("http://www.customs.gov.cn/customs/302249/302266/302267/2011fa33-%s.html", i));
//                } else if("302268".equals(tag)) {
//                    realHtml = GetHtml.getHtml("http://www.customs.gov.cn/customs/302249/302266/302268/index.html");
//                } else {
//                    realHtml = GetHtml.getHtml(String.format("http://www.customs.gov.cn/eportal/ui?pageId=302269" +
//                            "&currentPage=%s&moduleId=fbabca72040440eeae48509739453d32", i));
//                }
//                Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//                while (matcher.find()) {
//                    if (Integer.parseInt(matcher.group(3)) > limitYear) {
//                        String policyUrl = "http://www.customs.gov.cn" + matcher.group(1);
//                        System.out.println(policyUrl);
//                        System.out.println(matcher.group(2));
//
//                        policy.setPolicyUrl(policyUrl);
//                        policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", ""));
//                        policy.setPolicySource(sourceName);
//                        policy.setPolicyKeywords("");
//                        policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
//                        policy.setPolicyContent("");
//                        try {
//                            String policyHtml = GetHtml.getHtml(policyUrl);
//                            Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                            while (matcher1.find()) {
//                                String text = matcher1.group(1).replaceAll("<style[\\s\\S]*?/style>|<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
//                                policy.setPolicyContent(text.trim());
//                            }
//                            InfoSave.insert(policy);
//                        } catch (Exception e) {
//                            System.out.println("multichoice");
//                        }
//                    }
//                }
//            }
//        }
//    }
//


//    public static void cbrc(Policy policy, String sourceUrl, String sourceName, String sourceRule) {
//        String content = "<div class=\"timu STYLE6\">([\\s\\S]*?)</center>";
//        int pages = GetPage.getCbrcPage(GetHtml.getHtml("http://www.cbrc.gov.cn/more.do?itemUuid=800103&current=1"));
//        for (int i = 1; i <= pages; i++) {
//            String realHtml;
//            realHtml = GetHtml.getHtml(String.format("http://www.cbrc.gov.cn/more.do?itemUuid=800103&current=%s", i));
//            Matcher matcher = Pattern.compile(sourceRule).matcher(realHtml);
//
//            while (matcher.find()) {
//                if (Integer.parseInt(matcher.group(3)) > limitYear) {
//                    String policyUrl = "http://www.cbrc.gov.cn" + matcher.group(1);
//                    System.out.println(policyUrl);
//                    System.out.println(matcher.group(2).trim());
//
//                    policy.setPolicyUrl(policyUrl);
//                    policy.setPolicyTitle(matcher.group(2).replaceAll("&.*?;", "").trim());
//                    policy.setPolicySource(sourceName);
//                    policy.setPolicyKeywords("");
//                    policy.setPublishTime(matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5));
//                    policy.setPolicyContent("");
//
//                    String policyHtml = GetHtml.getHtml(policyUrl);
//                    Matcher matcher1 = Pattern.compile(content).matcher(policyHtml);
//                    while (matcher1.find()) {
//                        String text = matcher1.group(1).replaceAll("<style[\\s\\S]*?/style>|<.*?>|&.*?;|\\s|<!--[\\s\\S]*?-->", "");
//                        policy.setPolicyContent(text.trim());
//                    }
//                    InfoSave.insert(policy);
//                }
//            }
//        }
//    }

}
