package com.spider.policy.bases;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import okhttp3.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.http.HttpStatus;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                .connectTimeout(80000, TimeUnit.SECONDS)
                .readTimeout(80000, TimeUnit.SECONDS)
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

    public static String getHtml3(String url){
        // 添加 readTimeout 解决 read超时报错问题
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(80000, TimeUnit.SECONDS)
                .readTimeout(80000, TimeUnit.SECONDS)
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
            byte[] b = body.bytes();
            html = new String(b, "GB2312");

            body.close();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return html;
    }

    public static void getHtml4(String url){
        // 添加 readTimeout 解决 read超时报错问题
        String script = "<script>([\\s\\S]*?)</script>";
        Set<Cookie> cookie1 = null;
        HtmlUnitDriver driver = new HtmlUnitDriver(true);
        driver.get(url);

        String html = driver.getPageSource();
        Matcher matcher1 = Pattern.compile(script).matcher(html);
        while (matcher1.find()) {
            html = "<script>" + matcher1.group(1) + "</script>";
            System.out.println(html);
        }

        cookie1 = driver.manage().getCookies();
        System.out.println("==============================================================");
        String c1 = String.valueOf(cookie1.toArray()[0]).split(";")[0];
        System.out.println(c1);
        System.out.println("==============================================================");

        String resHtml = "function getClearance(){" + html + "};";
        resHtml = resHtml.replace("</script>", "");
        resHtml = resHtml.replace("eval", "return");
        resHtml = resHtml.replace("<script>", "");

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        try {
            engine.eval(resHtml);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        Invocable invocable = (Invocable) engine;
        String resJs = null;
        try {
            resJs = (String) invocable.invokeFunction("getClearance");
        } catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        //一级解密结果
        System.out.println(resJs);


        assert resJs != null;
        String overJs="function getClearance2(){ var a" + resJs.split("document\\.cookie")[1].split("Path=/;'")[0]+"Path=/;';return a;};";
        overJs=overJs.replace("!window['callP'+'hantom']", "''");
        System.out.println(overJs);
        try {
            engine.eval(overJs);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        Invocable invocable2 = (Invocable) engine;
        String over = null;
        try {
            over = (String) invocable2.invokeFunction("getClearance2");
        } catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        // 得到__jsl_clearance
        String c2 = over.split(";")[0];
        System.out.println(c1 + "; " + c2);

        System.out.println(c1.split("=")[0] + "," + c1.split("=")[1]);

        HtmlUnitDriver driver2 = new HtmlUnitDriver();
        driver2.setJavascriptEnabled(true);
        driver2.get(url);
        driver2.manage().addCookie(new Cookie(c1.split("=")[0], c1.split("=")[1]));
        driver2.manage().addCookie(new Cookie(c2.split("=")[0], c2.split("=")[1]));

        System.out.println(driver2.getPageSource());
        driver2.quit();


    }

    public static String getHtml5(String url) {
        // 添加 readTimeout 解决 read超时报错问题
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(80000, TimeUnit.SECONDS)
                .readTimeout(80000, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
//                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36")
//                .header("Host", "www.chinatax.gov.cn")
//                .header("Pragma", "no-cache")
//                .header("Referer", "http://www.chinatax.gov.cn/n810341/n810755/c4195742/content.html")
//                .header("Upgrade-Insecure-Requests", "1")
                .header("Cookie","_Jo0OQK=2E0DB064660FBA71C8005795F5A22E00880FE963A7C36CC717FC0CE0B38CF5CF5680F278E697299F339CB480CEAD3B0678A1CDAC8A859521FACCD2808D7A2E4675434275DAD340EB4DDFFF13AA80B4DD4EFFFF13AA80B4DD4EFAB58C038EE9C1C1121CDDF25DF461B2DGJ1Z1VA==;")
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

    public static void main(String[] args) {
//        getHtml4("http://www.cbrc.gov.cn/govView_F26E79837C9A48878A1B6616BB80FF73.html");
        getHtml5("http://www.chinatax.gov.cn/n810341/n810755/c4199190/content.html");
    }
}
