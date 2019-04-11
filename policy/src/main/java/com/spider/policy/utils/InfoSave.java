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
            String sql = "INSERT INTO `policies`(`source_id`, `source_name`, `country`, `province`, " +
                    "`city`, `tag`, `url`, `title`, `content`, `published`) VALUES (?,?,?,?,?,?,?,?,?,?)";
            pst = connection.prepareStatement(sql);

            // 查询是否存在相同的id
            String querySql = "select `url` from `policies` where `url`=?";
            PreparedStatement pst2 = connection.prepareStatement(querySql);
            pst2.setString(1, policy.getUrl());
            ResultSet result = pst2.executeQuery();

            // 不存在对应id
            if (!result.next()) {
                // setXxx()方法设置占位符的值，索引从1开始 setLong() 存时间戳
                pst.setInt(1, policy.getSourceId());
                pst.setString(2, policy.getSourceName());
                pst.setInt(3, policy.getCountry());
                pst.setString(4, policy.getProvince());
                pst.setString(5, policy.getCity());
                pst.setString(6, policy.getTag());
                pst.setString(7, policy.getUrl());
                pst.setString(8, policy.getTitle());
                pst.setString(9,policy.getContent());
                pst.setTimestamp(10, policy.getPublished());
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

    public static void insertSource(Source source) {
        try {
            Connection connection = dbConnect.getConnect();
            PreparedStatement pst;

            String insertSql = "INSERT INTO source (`domain`, `name`, `country`, `province`, `city`) VALUES (?,?,?,?,?)";
            pst = connection.prepareStatement(insertSql);

            String selectSql = "select `domain` from source where domain=?";
            PreparedStatement pst2 = connection.prepareStatement(selectSql);
            pst2.setString(1, source.getDomain());
            ResultSet result = pst2.executeQuery();

            // 返回结果集
            if (!result.next()) {
                pst.setString(1, source.getDomain());
                pst.setString(2, source.getName());
                pst.setInt(3, source.getCountry());
                pst.setString(4, source.getProvince());
                pst.setString(5, source.getCity());

                pst.executeUpdate();
                System.out.println("==");
            } else {
                System.out.println("source exists");
            }
            pst2.close();
            pst.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Source> selectSource() {
        ArrayList<Source> sourceArrayList = new ArrayList<>();
        try {
            Connection connection = dbConnect.getConnect();
            PreparedStatement pst;

            String querySql = "select `id`, `domain`, `name`, `country`, `province`, `city` from `source`";
            pst = connection.prepareStatement(querySql);
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                Source source = new Source();
                source.setId(result.getInt(1));
                source.setDomain(result.getString(2));
                source.setName(result.getString(3));
                source.setCountry(result.getInt(4));
                source.setProvince(result.getString(5));
                source.setCity(result.getString(6));

                sourceArrayList.add(source);
            }
            pst.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sourceArrayList;
    }

    public static ArrayList<SourceList> selectSourceList() {
        ArrayList<SourceList> sourceListArrayList = new ArrayList<>();
        try {
            Connection connection = dbConnect.getConnect();
            PreparedStatement pst;

            String querySql = "select `source_id`, `url`, `tag`, `use_tool`, `header`, `regular`, `title_reg`, `content_reg`, " +
                    "`page_reg`, `time_reg`, `page_startnum`, `page_lastnum`, `monitor`, `morepage` from `source_list`";
            pst = connection.prepareStatement(querySql);
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                SourceList sourceList = new SourceList();
                sourceList.setSourceId(result.getInt(1));
                sourceList.setUrl(result.getString(2));
                sourceList.setTag(result.getString(3));
                sourceList.setUseTool(result.getInt(4));
                sourceList.setHeader(result.getString(5));
                sourceList.setRegular(result.getString(6));
                sourceList.setTitleReg(result.getString(7));
                sourceList.setContentReg(result.getString(8));
                sourceList.setPageReg(result.getString(9));
                sourceList.setTimeReg(result.getString(10));
                sourceList.setPageStartNum(result.getInt(11));
                sourceList.setPageLastNum(result.getInt(12));
                sourceList.setMonitor(result.getInt(13));
                sourceList.setMorePage(result.getInt(14));

                sourceListArrayList.add(sourceList);
            }
            pst.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sourceListArrayList;
    }

}
