package com.spider.policy.bases;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

/**
 * @PackageName com.spider.kan360.videoinfo
 * @Author joel
 * @Date 2019/1/31 16:25
 **/

public class GetHtml {
    /**
     * 获取网页源码
     */
    public static String getHtml(String url){
        // 添加 readTimeout 解决 read超时报错问题
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(8000, TimeUnit.SECONDS)
                .readTimeout(8000, TimeUnit.SECONDS)

                .build();

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36")
                .build();

        String html = null;
        try {
            Response response = client.newCall(request).execute();

            // 响应
            if (!response.isSuccessful()) {
                throw new IOException("服务端错误" + response);
            }

            // 正文
            ResponseBody body = response.body();
            assert body != null;

            // 获取返回的数据，可通过response.body().string()获取，默认返回的是utf-8格式
            html = body.string();

            body.close();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return html;
    }

    public static String getHtml2(String url){
        // 添加 readTimeout 解决 read超时报错问题
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(8000, TimeUnit.SECONDS)
                .readTimeout(8000, TimeUnit.SECONDS)

                .build();

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "zh-CN,zh;q=0.9")
                .header("Cache-Control", "no-cache")
                .header("Connection", "keep-alive")
                .header("Cookie", "__jsluid=cce6cd0dcb2aed52f95fcf69e171da01; wwwcookie=20111112; UM_distinctid=169e1bfb83bca-0a1245f54087a5-4313362-37232c-169e1bfb83da80; gwdshare_firstime=1554270632665; _gscu_2110097639=54270632vp7ep716; _gscbrs_2110097639=1; _gsref_2110097639=http://samr.saic.gov.cn/; __jsl_clearance=1554287526.695|0|2oX3JhXImQc1InXU9m3DVXPMpJA%3D; CNZZDATA1276383350=1141468866-1554265967-null%7C1554287577; _gscs_2110097639=t54286517lmfm2b16|pv:30")
                .header("Host", "samr.saic.gov.cn")
                .header("Pragma", "no-cache")
                .header("Referer", "http://samr.saic.gov.cn/zw/zcfg/index_1.html")
                .header("Upgrade-Insecure-Requests", "1")
                .build();

        String html = null;
        try {
            Response response = client.newCall(request).execute();

            // 响应
            if (!response.isSuccessful()) {
                throw new IOException("服务端错误" + response);
            }

            // 正文
            ResponseBody body = response.body();
            assert body != null;

            // 获取返回的数据，可通过response.body().string()获取，默认返回的是utf-8格式
            html = body.string();

            body.close();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return html;
    }

}
