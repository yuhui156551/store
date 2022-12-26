# store
## 商城项目

### 系统开发及运行环境
Java开发包：JDK 8

数据库：MySQL 8.0.30

服务器架构：Spring Boot 2.7.6 + MyBatis 2.3.0 + AJAX

### 前言
  上面的运行环境仅供参考，如果你不是jdk17及以上的话，不建议使用boot3，运行会出错。
  
  项目特别简单，适合刚学boot的。
  
  技术栈直接看pom文件就好了，SpringBoot2就不用多说了，mybatis框架，很适合用来熟悉sql语句，毕竟企业开发主流还是mybatis，等很熟悉sql语句的时候，再去用mybatisplus吧，lombok记录日志、简化实体类的书写，AspectJ用来做AOP检测方法执行时长，前端用的是JQuery，技术栈很简单，其他的没什么亮点了，乏善可陈。
  
  只实现了部分功能：用户注册登录、修改资料、上传头像、收获管理、热销商品、购物车、订单等。具体内容可以google下载octotree插件预览代码或者下载源码（注释很多，不怕看不懂）。
  
  下面是一些效果图，如果看不了的话，建议科学上网（你懂的）。

### 主页面
 ![image](https://github.com/yuhui156551/store/blob/master/imag/20221219185112.png)
### 管理用户信息
 ![image](https://github.com/yuhui156551/store/blob/master/imag/20221219185217.png)
### 加入购物车
 ![image](https://github.com/yuhui156551/store/blob/master/imag/20221219185442.png)
### 生成订单
 ![image](https://github.com/yuhui156551/store/blob/master/imag/20221219185541.png)
