package com.spider.policy.bases;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @PackageName com.spider.kan360.aizhan
 * @Author joel
 * @Date 2019/3/19 11:57
 **/
public class GetProxy {
    public static ArrayList<ArrayList<String>> getProxy() {
        ArrayList<ArrayList<String>> hostPortArray = new ArrayList<>();
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(8000, TimeUnit.SECONDS)
                .readTimeout(8000, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url("http://116.236.178.19:8282/adsl")
                .header("X-SUOSI-PROXY", "1")
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

            String getk = "\\[(.*?)]";
            String gethp = "\"(.*?):(.*?)\"";

            Pattern pattern = Pattern.compile(getk);
            Matcher matcher = pattern.matcher(html);

            // 正则提取基本视频信息
            while (matcher.find()) {
                Matcher matcher1 = Pattern.compile(gethp).matcher(matcher.group(1));
                while (matcher1.find()) {
                    ArrayList<String> hostPort = new ArrayList<>();
                    String host = matcher1.group(1);
                    String port = matcher1.group(2);
                    hostPort.add(host);
                    hostPort.add(port);
                    hostPortArray.add(hostPort);
//                    System.out.println(host + ":" + port)
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return hostPortArray;
    }

    public static ArrayList<String> getRandomProxy() {
        ArrayList<ArrayList<String>> hostPortList = getProxy();
        Random random = new Random();
        int n = random.nextInt(hostPortList.size());

        return hostPortList.get(n);
    }

}
