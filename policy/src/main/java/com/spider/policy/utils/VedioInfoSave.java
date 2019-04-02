package com.spider.policy.utils;

import com.spider.policy.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @PackageName com.spiders.kan360.utils.VedioInfoSave
 * @Author joel
 * @Date 2019/1/21 10:47
 **/
public class VedioInfoSave {
    private static DbConnect dbConnect = new DbConnect();

    /**
     * 存储视频（电影、电视剧）的基本信息 38426
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

    /**
     * 存储vedio_ids
     */
    public static void insertVedioIds(String vedioId, String vedioName, Timestamp createtime) {
        try {
            Connection connection = dbConnect.getConnect();
            PreparedStatement pst;
            String sql = "INSERT INTO `vedio_ids`(`vedio_id`,`vedio_name`,`createtime`) VALUES (?,?,?)";
            pst = connection.prepareStatement(sql);

            String querySql = "select `vedio_id` from `vedio_ids` where `vedio_id`=?";
            PreparedStatement pst2 = connection.prepareStatement(querySql);
            pst2.setString(1, vedioId);
            ResultSet result = pst2.executeQuery();

            String updateSql = "update `vedio_ids` set `iscrawl`=0 where `vedio_id`=?";
            PreparedStatement pst3 = connection.prepareStatement(updateSql);
            pst3.setString(1, vedioId);

            if (!result.next()) {
                // setXxx()方法设置占位符的值，索引从1开始 setLong() 存时间戳
                pst.setString(1, vedioId);
                pst.setString(2, vedioName);
                pst.setTimestamp(3, createtime);
                // 执行SQL语句
                pst.executeUpdate();
                System.out.println("vedioids==");
            } else {
                System.out.println("update==");
                pst3.executeUpdate();
            }
            pst3.close();
            pst2.close();
            pst.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新 vedio_ids 的未采集视频，设置 iscrawl=1
     */
    public static void updateVedioIds(String vedioId) {
        try {
            Connection connection = dbConnect.getConnect();
            PreparedStatement pst;

            // 查询导演、演员的名称
            String querySql = "update `vedio_ids` set iscrawl=1 where vedio_id=?";
            pst = connection.prepareStatement(querySql);
            pst.setString(1, vedioId);
            // execute update SQL stetement
            pst.executeUpdate();
            System.out.println("success update");
            pst.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询8万演员名称，因在存入数据库之前就已排重，HashSet，之后通过搜索接口查询视频
     */
    public static HashSet<String> selectMoreActor() {
        HashSet<String> names = new HashSet<>();
        try {
            Connection connection = dbConnect.getConnect();
            PreparedStatement pst;

            String querySql = "select `actorname` from `actor`";
            pst = connection.prepareStatement(querySql);
            ResultSet result = pst.executeQuery();

            // 返回结果集
            while (result.next()) {
                // 执行SQL语句
                String res = result.getString(1);
                System.out.println(res);

                names.add(res);
            }
            pst.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return names;
    }

    /**
     * 从原来的 kan_allvedio 中提取演员名称，重新搜索，保存数据
     */
    public static HashSet<ArrayList<Object>> selectIdPeople(String field) {
        HashSet<ArrayList<Object>> names = new HashSet<>();
        try {
            Connection connection = dbConnect.getConnect();
            PreparedStatement pst;

            String querySql = String.format("select `id`, %s from `kan_allvedio`", field);
            pst = connection.prepareStatement(querySql);
            ResultSet result = pst.executeQuery();

            // 返回结果集
            while (result.next()) {
                // 执行SQL语句

                int id = result.getInt(1);
                String yanyauns = result.getString(2);
                String[] yanyuanlist = yanyauns.split("/");

                for (String yanyaun : yanyuanlist) {
                    ArrayList<Object> idPeople = new ArrayList<>();
                    idPeople.add(id);
                    idPeople.add(yanyaun);
                    System.out.println(idPeople);
                    names.add(idPeople);
                }
            }
            pst.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return names;
    }

    /**
     * 从原来的 kan_allvedio 中提取演员名称，重新搜索，保存数据
     */
    public static void insertPeople(String table, String field, int vedioId, String actorname) {
        try {
            Connection connection = dbConnect.getConnect();
            PreparedStatement pst;

            String insertSql = String.format("INSERT INTO %s (`vedio_id`, %s) VALUES (?,?)", table, field);
            pst = connection.prepareStatement(insertSql);

            String selectSql = String.format("select %s from %s where %s=?", field, table, field);
            PreparedStatement pst2 = connection.prepareStatement(selectSql);
            pst2.setString(1, actorname);
            ResultSet result = pst2.executeQuery();

            // 返回结果集
            if (!result.next()) {
                pst.setInt(1, vedioId);
                pst.setString(2, actorname);

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

    /**
     * 查询电视剧，返回HashSet
     */
    public static HashSet<String> selectSource() {
        HashSet<String> sourceUrl = new HashSet<>();
        try {
            Connection connection = dbConnect.getConnect();
            PreparedStatement pst;

            String querySql = "select `source_url` from `source`";
            pst = connection.prepareStatement(querySql);
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                String res = result.getString(1);
                sourceUrl.add(res);
            }
            pst.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sourceUrl;
    }

}
