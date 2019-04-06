package com.spider.policy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindDate {
    public static String findDate(String html) {
        String dates = "";
        String dateRule = "(\\d{4})年(\\d+)月(\\d+)日";
        Matcher matcher = Pattern.compile(dateRule).matcher(html);
        while (matcher.find()) {
            dates = matcher.group(1) + "-" + matcher.group(2) + "-" + matcher.group(3);
        }
        return dates;
    }
}
