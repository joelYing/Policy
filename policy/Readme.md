# Policy
采集指定网站政策类信息，并监控定时爬取

## 需求

* 采集不同网站的政策类信息
* 采集历史数据信息；定时，监控爬取指定页面
* 前端通过条件搜索（根据标题，来源，排序等）展示对应的信息列表，可以通过点击直接跳转到信息页面；也可以点击预览，查看该信息正文等内容
* 前端提供插入数据源的接口，插入采集规则的接口

### 更新需求

* 通过设置 monitor=1 指定需要定时监控的页面，每天定时爬取；整体可一周爬取一次

## 接口

### 数据源插入接口
* http://127.0.0.1:8080/insertSource 
```
插入数据源信息 如（可参照source表中数据）：

domain      : spb.gov.cn   // 域名
name        : 国家邮政局	   // 来源名称
country     : 1	           // 1 表示来源为中央或国家级网站；0 表示地方及其他，默认为0
province    :              // 若为省、市级网站，则添加，如上海；默认为空
city        :              // 城市，可添加，如上海；默认为空
```

### 采集规则插入接口
* http://127.0.0.1:8080/insertSourceList
```
插入数据源采集规则信息 如（可参照source_list表中数据）：

source_id       : 1             // 对应的来源网站的ID
url             : http://www.mofcom.gov.cn/article/b/     // 爬取页面的url 
tag             : 政策发布       // 爬取页面所属分类标签
use_tool        : 1             // 是否能用 PageExtract 解析工具解析页面得到内容页链接，可用 TestPage.java 测试
header          :               // 添加请求头信息，如 Cookie:xxx 可选（针对需要添加请求信息的网站）
regular         : http://www.mofcom.gov.cn/article/\w/\w*?/\d+/\d+.shtml  // 使用 TestPage.java 解析，限制提取的内容页链接（默认空）
title_reg       : <title>([\s\S]*?)</title>             // 标题正则 默认为空    
content_reg     : [<!--|<!-- ]文章正文.*?>([\s\S]*?)<script>		// 正文正则 默认为空
page_reg        :               // 总页数正则，针对需要爬取多页的页面，找到源代码中对应的页数 默认为空
time_reg        : var tm = "(\d+)-(\d+)-(\d+) (\d+):(\d+):(\d+)";    // 发布时间正则 
page_startnum   : 0           // 页面起始页码
page_lastnum    : 0           // 页面结束页码 针对多页爬取，可以手动选择爬取范围 （当有page_reg为空时，代码才会使用这个）
monitor         : 1           // 监控标识，设置为1，即标为每天都采集的页面
morepage        : 0           // 判断是否爬取多页，为0 表示只取当前链接
```

### 查询接口
* http://127.0.0.1:8080/queryPolicy
```
根据 模糊匹配（标题、来源【二者可为空】），排序规则（desc or asc，【不可为空】）查询符合条件的政策类信息

可能还需要添加根据 province、city查询
```

### 查询结果
* http://127.0.0.1:8080/allPolicy
```
点击查询后得到的界面，点击链接可跳转，点击预览跳转到正文预览
```

### 预览
* http://127.0.0.1:8080/preview?url=http://xxgk.mot.gov.cn/jigou/zhishudw/201904/t20190417_3188427.html
```
预览页面
```

## 主要项目结构
```
bases\GetHtml                 ： 用于添加请求头信息的网页源代码获取
config\PageHelperConfig       : pageHelper 分页插件的配置信息
entity\*                      ： 实体类
mapper\PolicyMapper            : mapper 文件 注解方式实现的对数据库的操作
service\PolicyService          : 服务类接口
service\impl\PolicyServiceImpl : 服务类实现类
threadtool                      : 多线程使用（暂时没用）
utils\DbConnect                : 数据库连接池
utils\GetSource                : 具体爬取类
utils\InfoSave                 : 对数据库操作
web\PolicyController           : controller类，包含接口的定义
NewPolicyTest                  ： 测试sourceList中指定ID的规则能否使用
QuartzService                  ： 定时任务
TestPage                       : 测试工具，对输入的链接，看能否提取内容页链接（可指定正则筛选需要的链接）

templates\insertSource         : 插入数据源的页面
templates\result               : 插入数据源结果返回页面

templates\insertSourceList     : 插入数据采集规则的页面
templates\resultList           : 插入数据采集规则结果返回页面
templates\policy               : 政策列表展示
templates\preview              : 预览页面
templates\query                : 查询页面
```

### TODO
* 页面优化
