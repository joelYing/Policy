### 总流程
初始：
1、运行Circle中的 m() ，tv() 将得到的视频url保存到TXT中，并将url中的id部分通名称一起存入 vedio_ids 中，iscrawl为0表示未抓取
2、Crawls中的crawl() 遍历 vedio_ids 中 iscrawl=0 的视频，采集对应的视频信息，封面图片本地化及播放源信息入库，并设 iscrawl=1
3、IDPeople 执行后，遍历 allvedio中的演员与导演名称，并存入对应的关联表中
4、ActorInfo 提取allvedio中导演跟演员的名称合成一个集合，遍历集合然后采集存在的人物信息，人物图片本地化
更新：
1、Crawls.update() 遍历360影视的createtime和updatetime接口，对于这些视频的url，同样存入vedio_ids中，但是将已存在的vedio 的 iscrawl置0，防止漏下更新
2、再执行 Crawls 中的crawl() 遍历 vedio_ids 中 iscrawl=0 的视频，采集对应的视频信息，封面图片本地化及播放源信息入库，并设 iscrawl=1

### 一、mybatis
#### 问题
```
多条件模糊查询时要注意，之前老是出现  Parameter 'vedioName' not found. Available parameters are [arg1, arg0, param1, param2]
的问题 原因大概是 Dao层接口定义和Mapper中的参数类型不一致
```
#### 解决方法
```
Controller层 有多个参数时，需要加上参数注解 @RequestParam("")
而与之对应的 在 Mapper 中也要加上参数注解  @Param("")
```

### 二、SQL语句
```
传入 order by ... 时需要注意
# 是把传入的数据当作字符串，如#{field}传入的是id,则sql语句生成是这样，order by "id",   根据id排序时会报错．． 
$ 传入的数据直接生成在sql里，如${field}传入的是id,则sql语句生成是这样，order by id,   根据id排序这种写法是正确的． 
```

### 三、多线程
```
ThreadPoolExecutor 使用

1、使用LinkedBlockingQueue作为workQueue，ThreadPoolExecutor对象在队列不满的情况下只会创建core线程，不会创建非core线程，设置的maximumPoolSize并不起作用

2、线程池最后应该当关闭 -- shutdown()
```

### 四、保存图片时
```
1、java.io.IOException: Server returned HTTP response code: 403 for URL: https://p.ssl.so.com/p/http%3A%2F%2Fw.qhimg.com%2Fimages%2Fv2%2Fhao%2Fhao360%2Fqyx%2Fzhuiai.jpg

图片防盗链，添加headers

2、java.net.MalformedURLException: no protocol

no protocol，没有指定通信协议异常。没有指定 http 协议，在 URL 前面加上http://即可解决此异常。

3、类似以下的视频海报链接会有问题，考虑添加headers解决

java.io.IOException: Server returned HTTP response code: 403 for URL
https://p.ssl.so.com/p/http%3A%2F%2Fw.qhimg.com%2Fimages%2Fv2%2Fhao%2Fhao360%2Fqyx%2Fzhuiai.jpg

4、java.io.FileNotFoundException: D:\360KanVedio\谁是G?.jpg (文件名、目录名或卷标语法不正确。)

小战象2 封面图片失效
```

### 五、多线程 + mysql更新
```
1、com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: The driver was unable to create a connection due to an inability to establish the client portion of a socket.
This is usually caused by a limit on the number of sockets imposed by the operating system. This limit is usually configurable. 
For Unix-based platforms, see the manual page for the 'ulimit' command. Kernel or system reconfiguration may also be required.
For Windows-based platforms, see Microsoft Knowledge Base Article 196271 (Q196271).
at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:39)

出现该问题的主要原因：每次插入一条数据就建立一个connection，导致链接异常！

应当建立一个数据库连接池，使用druid时要注意缓冲池的参数
```

### 六、正则
```
1、解析电视剧播放站点链接 ； *? 表示非贪婪模式，匹配到尽可能少的内容

2、[\\s\\S]*? 能匹配所有； (.*?) 不能匹配 \n
```

### 七、mysql批量更新死锁
```
1、mysql的事务支持与存储引擎有关，MyISAM不支持事务，INNODB支持事务，更新时采用的是行级锁。这里采用的是INNODB做存储引擎，意味着会将update语句做为一个事务来处理。前面提到行级锁必须建立在索引的基础，这条更新语句用到了索引idx_1，所以这里肯定会加上行级锁。
  
  行级锁并不是直接锁记录，而是锁索引，如果一条SQL语句用到了主键索引，mysql会锁住主键索引；如果一条语句操作了非主键索引，mysql会先锁住非主键索引，再锁定主键索引。
  
  这个update语句会执行以下步骤：
  
  1、由于用到了非主键索引，首先需要获取idx_1上的行级锁
  
  2、紧接着根据主键进行更新，所以需要获取主键上的行级锁；
  
  3、更新完毕后，提交，并释放所有锁。
  
  如果在步骤1和2之间突然插入一条语句：update user_item .....where id=? and user_id=?,这条语句会先锁住主键索引，然后锁住idx_1。
  
  蛋疼的情况出现了，一条语句获取了idx_1上的锁，等待主键索引上的锁；另一条语句获取了主键上的锁，等待idx_1上的锁，这样就出现了死锁。

解决方法：修改表的主键，尽量按 主键 来确定更新语句中的条件限制
在采用INNODB的MySQL中，更新操作默认会加行级锁，行级锁是基于索引的，在分析死锁之前需要查询一下mysql的执行计划，看看是否用到了索引，用到了哪个索引，对于没有用索引的操作会采用表级锁。如果操作用到了主键索引会先在主键索引上加锁，然后在其他索引上加锁，否则加锁顺序相反。在并发度高的应用中，批量更新一定要带上记录的主键，优先获取主键上的锁，这样可以减少死锁的发生。

```

* 彻底解决unable to find valid certification path to requested target
<https://blog.csdn.net/frankcheng5143/article/details/52164939>

* Caused by: java.lang.IllegalArgumentException: 不支持：http://javax.xml.XMLConstants/property/accessExternalDTD
<https://community.pega.com/support/support-articles/error-occurs-when-using-soap-integartion-parse-xml>
```
@SpringBootApplication
@EnableScheduling
@MapperScan("com.spider.policy.mapper")
public class PolicyApplication {
    public static void main(String[] args) {
        System.setProperty("javax.xml.transform.TransformerFactory","com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
        SpringApplication.run(PolicyApplication.class, args);
    }
}
```