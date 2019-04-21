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
     * 每天14点启动 五分半
     */
    @Scheduled(cron = "0 40 9 1/1 * ?")
    public void updateCrawl() throws InterruptedException {
        GetSource.getMonitorLists();
        System.out.println("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

}

