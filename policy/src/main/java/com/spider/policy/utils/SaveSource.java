package com.spider.policy.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @PackageName com.spider.policy.utils
 * @Author joel
 * @Date 2019/4/3 13:03
 **/
public class SaveSource {
    public static void saveSource() {
        File file = new File("D:\\mygit\\policy_git\\Policy\\policy\\src\\main\\java\\com\\spider\\policy\\bases\\source.txt");
        try {
            List<String> sources = FileUtils.readLines(file, "UTF-8");
            for(String s : sources) {
                String[] ss = s.split(" ");
                String name = ss[0];
                String url = ss[1];
                System.out.println(name + ": " + url);
//                InfoSave.insertSource(name, url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        saveSource();
    }
}
