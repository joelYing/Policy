package com.spider.policy.threadtool;

import com.spider.policy.bases.KanId;
import lombok.Data;

import java.util.concurrent.CountDownLatch;

/**
 * @PackageName com.spider.policy.threadtool
 * @Author joel
 * @Date 2019/3/8 18:34
 **/
@Data
public class CircleTask implements Runnable {
    private String url;
    private int status =99;
    private CountDownLatch threadsSignal;

    public CircleTask(String realUrl) {
        this.url = realUrl;
    }

    @Override
    public void run() {
        KanId.getIdList(url);
        status=0;
        threadsSignal.countDown();
    }
}

