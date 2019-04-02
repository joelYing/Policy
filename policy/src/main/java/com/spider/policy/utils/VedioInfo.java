package com.spider.policy.utils;

import com.alibaba.fastjson.JSON;
import com.spider.policy.entity.VedioInfos;
import com.spider.policy.bases.GetHtml;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @PackageName com.spider.kan360.utils
 * @Author joel
 * @Date 2019/2/14 14:31
 **/

public class VedioInfo {
    HashSet<String> selectSource = VedioInfoSave.selectSource();

    /**
     * 采集视频的基本信息以及视频对应的播放链接，并存入数据库
     */
    public static void getVedioInfoHtml(String url) {
        // 视频信息
        VedioInfos vedioInfos = new VedioInfos();
        // 演员信息采集
//        ActorInfo actorInfo = new ActorInfo();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 视频ID
        String id = url.replaceAll("https://www.360kan.com/.*?/|\\.html", "");
        vedioInfos.setVedioId(id);

        String vedioInfoHtml = GetHtml.getHtml(url);

        // (.*?) 不能匹配包含 \n 的内容 ([\s\S]*) 才可匹配任意字符
        // 匹配通用的基本视频信息
        String getBasicInfo = "大海报[\\s\\S]*<img src=\"(.*?)\">[\\s\\S]*<h1>(.*?)</h1>" +
                "[\\s\\S]*类型 ：([\\s\\S]*)</p>[\\s\\S]*年代 ：</span>(.*?)</p>[\\s\\S]*地区 ：" +
                "</span>(.*?)</p>[\\s\\S]*导演 ：" +
                "</span>([\\s\\S]*)</p>[\\s\\S]*item-actor\">[\\s\\S]*演员 ：</span>([\\s\\S]*)<div class=" +
                "\"item-desc-wrap g-clear js-open-wrap\">[\\s\\S]*" +
                "style=\"display:none;\"><span>简介 ：</span>([\\s\\S]*)收起&lt;";

        // 电影站点解析
        String getMPlaySite = "站点排序 ：([\\s\\S]*)<div style=' visibility:hidden'";

        // 电视剧站点json部分解析
        String getTvPlaySite = "\\{\"ensite\":\".*?\",\"cnsite\":\"(.*?)\",\"vip\":.*?}";

        // 判断是否存在付费
        String getPay = "<i class=\"ico-pay\"></i>";

        // 判断电视剧的最新一集
        String getNew = "<i class=\"ico-new\">";

        // 判断是否有评分
        String getScope = "<span class=\"s\">(.*?)</span>";

        // 获取电影播放站点连接
        String getMovieUrl = "http://(v.*?|www.*?|film.*?)\">(.*?)</a>";

        // 解析电视剧播放站点链接 ； *? 表示非贪婪模式，匹配到尽可能少的内容
        String getTvUrl = "<a.*?href=([\\s\\S]*)a>";
        String getTvClearUrl = "<a data-num=\"(\\d+)\" data-daochu=\".*?\" href=\"(.*?)\">(.*?)</a>";

        Pattern pattern = Pattern.compile(getBasicInfo);
        Matcher matcher = pattern.matcher(vedioInfoHtml);

        // 正则提取基本视频信息
        while (matcher.find()) {
            vedioInfos.setVedioUrl(url);
            vedioInfos.setCoverImg(matcher.group(1));

            vedioInfos.setVedioName(matcher.group(2));
            // 保存图片
//            downLoad(matcher.group(1), matcher.group(2), vedioImagePath);

            String types = matcher.group(3)
                    .replaceAll("</a>", "/")
                    .replaceAll("<.*?>|\\s", "")
                    .replaceAll("/", " ").trim();
            vedioInfos.setTypes(types);

            vedioInfos.setYear(matcher.group(4));
            vedioInfos.setArea(matcher.group(5));

            // 不能以空格作为分割
            String daoyan = matcher.group(6)
                    .replaceAll("</a>&nbsp;", "/")
                    .replaceAll("</a>&nbsp;|<.*?>|\\s", "").trim();
            daoyan = daoyan.substring(0, daoyan.length() - 1);
            vedioInfos.setDaoyan(daoyan);

            String yanyuan = matcher.group(7)
                    .replaceAll("</a>&nbsp;|<.*?>|\\s|影视百科|分集介绍", "").trim();
            // 采集演员信息
//            ActorInfo.getActorInfoHtml(yanyuan)
            vedioInfos.setYanyuan(yanyuan);

            String introduce = matcher.group(8).replaceAll("<.*?>|收起&lt;&lt;", "");
            vedioInfos.setIntroduce(introduce);
        }

        vedioInfos.setPay("免费");
        if (vedioInfoHtml.contains(getPay)) {
            vedioInfos.setPay("付费");
        }

        vedioInfos.setScore("");
        Matcher matcher2 = Pattern.compile(getScope).matcher(vedioInfoHtml);
        while (matcher2.find()) {
            vedioInfos.setScore(matcher2.group(1));
        }

        vedioInfos.setCreateTime(Timestamp.valueOf(simpleDateFormat.format(timestamp)));
        // 视频（电影、电视剧）的基本信息，入库
//        VedioInfoSave.insert(vedioInfos);
//        System.out.println(vedioInfos.getVedioId() + "\n" + vedioInfos.getVedioType() + "\n" + vedioInfos.getVedioName() + "\n" +vedioInfos.getCoverImg() + "\n" +
//                vedioInfos.getPay() + "\n" + vedioInfos.getScore() + "\n" + vedioInfos.getTypes() + "\n" + vedioInfos.getYear() + "\n" + vedioInfos.getArea() + "\n" +
//                vedioInfos.getDaoyan() + "\n" + vedioInfos.getYanyuan() + "\n" + vedioInfos.getIntroduce() + "\n" + vedioInfos.getSource());

    }

    public static void main(String[] args) {
        getVedioInfoHtml("https://www.360kan.com/tv/Qblrb07lRWLlOX.html");
//        downLoad("https://p.ssl.qhimg.com/t01582821660247b6c8.jpg", "巨齿鲨");
    }
}

