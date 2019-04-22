package com.spider.policy;

import com.spider.policy.utils.GetSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @PackageName com.spider.kan360
 * @Author joel
 * @Date 2019/3/12 10:46
 **/

@Component
public class QuartzService {
    /**
     * 每天早上10:30
     */
    @Scheduled(cron = "0 30 10 1/1 * ?")
    public void updateCrawl1() {
        GetSource.getMonitorLists();
        System.out.println("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    /**
     *  每周五早8点
     */
    @Scheduled(cron = "0 0 8 ? * FRI")
    public void updateCrawl2() {
        GetSource.getSourceLists();
        System.out.println("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

}

