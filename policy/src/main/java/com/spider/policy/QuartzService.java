package com.spider.policy;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import static com.spider.policy.bases.Circle.m;
import static com.spider.policy.bases.Circle.tv;

/**
 * @PackageName com.spider.kan360
 * @Author joel
 * @Date 2019/3/12 10:46
 **/

@Component
public class QuartzService {
    /**
     * 每天14点启动 五分半
     */
    @Scheduled(cron = "0 30 9 1/1 * ?")
    public void updateCrawl() throws InterruptedException {
        System.out.println("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

}

