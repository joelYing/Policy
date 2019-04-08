package com.spider.policy.utils;

import com.spider.policy.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @PackageName com.spiders.policy.utils.InfoSave
 * @Author joel
 * @Date 2019/1/21 10:47
 **/
public class InfoSave {
    private static DbConnect dbConnect = new DbConnect();

    /**
     * 存储政策
     */
    public static void insert(Policy policy) {
        try {
            Connection connection = dbConnect.getConnect();
            // PrepareStatement是 Statement的子接口，可以传入带占位符的 SQL语句，提供了补充占位符变量的方法，还可以防止SQL注入
            PreparedStatement pst;
            String sql = "INSERT INTO `policies`(`policy_url`, `policy_title`, `policy_content`, `policy_source`, " +
                    "`policy_keywords`, `publish_time`) VALUES (?,?,?,?,?,?)";
            pst = connection.prepareStatement(sql);

            // 查询是否存在相同的id
            String querySql = "select `policy_url` from `policies` where `policy_url`=?";
            PreparedStatement pst2 = connection.prepareStatement(querySql);
            pst2.setString(1, policy.getPolicyUrl());
            ResultSet result = pst2.executeQuery();

            // 不存在对应id
            if (!result.next()) {
                // setXxx()方法设置占位符的值，索引从1开始 setLong() 存时间戳
                pst.setString(1, policy.getPolicyUrl());
                pst.setString(2, policy.getPolicyTitle());
                pst.setString(3, policy.getPolicyContent());
                pst.setString(4, policy.getPolicySource());
                pst.setString(5, policy.getPolicyKeywords());
                pst.setString(6, policy.getPublishTime());
                // 执行SQL语句
                pst.executeUpdate();
                System.out.println("=====");
            } else {
                System.out.println("url已存在");
            }
            pst2.close();
            pst.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertSource(String name, String url) {
        try {
            Connection connection = dbConnect.getConnect();
            PreparedStatement pst;

            String insertSql = "INSERT INTO source (`source_url`, source_name) VALUES (?,?)";
            pst = connection.prepareStatement(insertSql);

            String selectSql = "select `source_url` from source where source_url=?";
            PreparedStatement pst2 = connection.prepareStatement(selectSql);
            pst2.setString(1, url);
            ResultSet result = pst2.executeQuery();

            // 返回结果集
            if (!result.next()) {
                pst.setString(1, url);
                pst.setString(2, name);

                pst.executeUpdate();
                System.out.println("==");
            } else {
                System.out.println("already exists");
            }
            pst2.close();
            pst.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ArrayList<String>> selectSource() {
        ArrayList<ArrayList<String>> sourceUrl = new ArrayList<>();
        try {
            Connection connection = dbConnect.getConnect();
            PreparedStatement pst;

            String querySql = "select `source_url`, `source_name` from `source`";
            pst = connection.prepareStatement(querySql);
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                ArrayList<String> urlname = new ArrayList<>();
                String sourceurl = result.getString(1);
                String sourcename = result.getString(2);
                urlname.add(sourceurl);
                urlname.add(sourcename);
                sourceUrl.add(urlname);
            }
            pst.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sourceUrl;
    }

}
