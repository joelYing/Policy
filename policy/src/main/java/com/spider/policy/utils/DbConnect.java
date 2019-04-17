package com.spider.policy.utils;

/**
 * @PackageName com.spider.policy.utils
 * @Author joel
 * @Date 2019/3/7 11:57
 */
import java.sql.Connection;
import com.alibaba.druid.pool.DruidDataSource;

/**
 * 数据库连接生成类，返回一个数据库连接对象
 * 构造函数完成数据库驱动的加载和数据的连接
 * 提供数据库连接的取得和数据库的关闭方法
 *
 */

public class DbConnect {
    private static DruidDataSource dataSource = null;
    /**
     * 构造函数完成数据库的连接和连接对象的生成
     */
    public DbConnect(){

    }

    public void getDbConnect() throws Exception  {
        try{
            if(dataSource==null){
                dataSource=new DruidDataSource();
                //设置连接参数
//                dataSource.setUrl("jdbc:mysql://localhost:3306/360kan?useUnicode=true&characterEncoding=UTF8&useSSL=false")
                dataSource.setUrl("jdbc:mysql://localhost:3306/policy?useUnicode=true&characterEncoding=UTF8&useSSL=false");
                dataSource.setDriverClassName("com.mysql.jdbc.Driver");
                dataSource.setUsername("root");
                dataSource.setPassword("12138");
                //配置初始化大小、最小、最大
                dataSource.setInitialSize(10);
                dataSource.setMinIdle(10);
                dataSource.setMaxActive(20);
                //连接泄漏监测
                dataSource.setRemoveAbandoned(true);
                dataSource.setRemoveAbandonedTimeout(30);
                //配置获取连接等待超时的时间
//                dataSource.setMaxWait(20000);
                //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
//                dataSource.setTimeBetweenEvictionRunsMillis(20000)
                // 防止过期
                dataSource.setValidationQuery("SELECT 'x'");
                dataSource.setTestWhileIdle(true);
//                dataSource.setTestOnBorrow(true)
            }
        }catch(Exception e){
            throw e;
        }
    }

    /**
     * 取得已经构造生成的数据库连接
     * @return 返回数据库连接对象
     */
    public Connection getConnect() throws Exception{
        Connection con = null;
        getDbConnect();
        con = dataSource.getConnection();
        return con;
    }

}
