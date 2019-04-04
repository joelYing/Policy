package com.spider.policy.bases;

import okhttp3.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
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

    public static String getHtml2(String url, String hostName, int port){
        // 添加 readTimeout 解决 read超时报错问题

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostName, port));
        Authenticator proxyAuthenticator = (route, response) -> {
            String credential = Credentials.basic("squid", "SuosiSquid147!$&");
            return response.request().newBuilder()
                    .header("Proxy-Authorization", credential)
                    .build();
        };

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(8000, TimeUnit.SECONDS)
                .readTimeout(8000, TimeUnit.SECONDS)
                .proxy(proxy)
                .proxyAuthenticator(proxyAuthenticator).build();


        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.93 Safari/537.36")
                .build();

        String html = "";

        try {
            Response response = client.newCall(request).execute();

            // 响应
            if (!response.isSuccessful()) {
                throw new IOException("服务端错误" + response);
            }

            // 正文
            ResponseBody body = response.body();
            assert body != null;

            html = body.string();
            body.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return html;
    }

}
