package com.spider.policy.threadtool;

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
//        somefunk();
        status=0;
        threadsSignal.countDown();
    }
}

