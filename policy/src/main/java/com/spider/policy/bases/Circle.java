package com.spider.policy.bases;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.spider.policy.threadtool.CircleTask;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @PackageName com.spider.kan360.videoinfo
 * @Author joel
 * @Date 2019/1/31 15:44
 **/

public class Circle {
    public static ThreadPoolExecutor getThreadPool() {
        return new ThreadPoolExecutor(20, 20,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100000),
                new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 电视剧
     * 9975
     *  基本信息、（kan_tv ok）
     */
    public static void tv() throws InterruptedException {
        String satrtUrl = "https://www.360kan.com/dianshi/list?year=all&area=all&act=all&cat=all";
        String formatUrl = "https://www.360kan.com/dianshi/list?year=%s&area=%s&act=all&cat=%s";

        String[] tvtypes = {"all", "101", "105", "109", "108", "111", "100", "104", "107", "103", "112", "102"
            , "116", "117", "118", "119", "120", "115", "114", "106", "113", "other"};

        String[] tvyears = {"all", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016"
            , "2017", "2018", "2019", "other"};

        String[] tvareas = {"all", "10", "11", "12", "13", "14", "15", "16", "17", "18"};

        List<CircleTask> circleTaskList = x(formatUrl, tvyears, tvareas, tvtypes);
        CountDownLatch threadSignal1 = new CountDownLatch(circleTaskList.size());
        ThreadPoolExecutor service1 = getThreadPool();
        for (CircleTask circleTask : circleTaskList) {
            circleTask.setThreadsSignal(threadSignal1);
            service1.execute(circleTask);
        }
        threadSignal1.await();

        // 解决大量重复
//        System.out.println(KanId.idUrl.size());
    }

    /**
     * 电影
     * 28389
     * 基本信息、(kan_m ok), 播放源（49990） OK
     * 先将遍历出来的 vedio_id 存入 vedio_ids 库中，iscrawl 默认为0
     */
    public static void m () throws InterruptedException {
        String formatUrl = "https://www.360kan.com/dianying/list?year=%s&area=%s&act=all&cat=%s";

        String[] mtypes = {"all", "101", "105", "109", "108", "111", "100", "104", "107", "103", "112", "102"
                , "116", "117", "118", "119", "120", "121", "122", "123", "115", "114", "106", "113", "other"};

        String[] myears = {"all", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016"
                , "2017", "2018", "2019", "other"};

        String[] mareas = {"all", "10", "11", "12", "13", "14", "15", "16", "17", "18", "21", "22", "other"};

        // 多线程循环获取

        List<CircleTask> circleTaskList = x(formatUrl, myears, mareas, mtypes);
        CountDownLatch threadSignal1 = new CountDownLatch(circleTaskList.size());
        ThreadPoolExecutor service1 = getThreadPool();
        for (CircleTask circleTask : circleTaskList) {
            circleTask.setThreadsSignal(threadSignal1);
            service1.execute(circleTask);
        }
        threadSignal1.await();

        File file = new File("D:\\mygit\\kan360\\src\\main\\java\\com\\spider\\kan360\\videoid\\allUrl.txt");
        try {
            FileUtils.writeLines(file, "UTF-8", KanId.idUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(KanId.idUrl.size());
        // 清空电影跟电视剧 所在的HashSet
        KanId.idUrl.clear();
    }

    /**
     * 综艺
     *
     */
    public static void va() {
        String formatUrl = "https://www.360kan.com/zongyi/list?cat=%s&act=%s&area=%s";

        String[] vacats = {"all", "101", "105", "109", "108", "111", "104", "107", "103", "112", "102"
                , "116", "117", "118", "119", "120", "115", "114", "106", "113", "110", "other"};

        String[] vaacts = {"all", "邓超", "陈赫", "何炅", "汪涵", "王俊凯", "黄磊", "谢娜", "黄渤", "周杰伦",
                "吴亦凡", "赵薇", "薛之谦", "Angelababy", "易烊千玺", "岳云鹏", "王嘉尔", "鹿晗", "杨幂", "沈腾",
                "罗志祥", "潘玮柏", "张艺兴", "华晨宇", "李维嘉", "钱枫", "宋小宝", "贾玲", "范冰冰", "沙溢", "撒贝宁",
                "涂磊"};

        String[] vaareas = {"all", "10", "11", "12", "13", "14", "15"};

        x(formatUrl, vacats, vaacts, vaareas);

        System.out.println(KanId.idUrl.size());

    }

    /**
     * 动漫
     * 13153
     */
    public static void ct() {
        String formatUrl = "https://www.360kan.com/dongman/list?cat=%s&year=%s&area=%s";

        String[] cttypes = {"all", "134", "100", "102", "109", "135", "136", "111", "107", "105", "137", "101"
        , "138", "106", "104", "110", "112", "131", "103", "108", "113", "115", "114", "123", "121", "119",
        "126", "116", "117", "127", "130", "128", "129", "132", "133"};

        String[] ctyears = {"all", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013",
                "2014", "2015", "2016", "2017", "2018", "2019", "other"};

        String[] ctareas = {"all", "10", "11", "12"};

        x(formatUrl, cttypes, ctyears, ctareas);

        System.out.println(KanId.idUrl.size());

    }


    /**
     * 多线程循环采集视频的url
     */
    private static List<CircleTask> x(String formatUrl, String[] aa, String[] bb, String[] cc) {
        List<CircleTask> circleTaskList = new ArrayList<>();

        for (String a : aa) {
            for (String b : bb) {
                for (String c : cc) {
                    String realUrl = String.format(formatUrl, a, b, c);
                    System.out.println(realUrl);

                    CircleTask circleTask = new CircleTask(realUrl);
                    circleTaskList.add(circleTask);
                }
            }
        }
        return circleTaskList;
    }



    public static void main(String[] args) throws InterruptedException {
//        tv();
        m();
//        va();
//        ct();
    }
}
