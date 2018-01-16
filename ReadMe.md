# 如何自动生成数据库访问代码
  修改gencode.properties 属性文件中entity_name，table_name
  运行GenerateCode 程序
  转移在test包下生成的新代码到main包下

# 数据访问文档参考 mybatis plus
  http://mp.baomidou.com/


#API文档访问地址
  http://localhost:8080/mgnt-app/assets/swagger/index.html

#swagger参考  
  http://www.jianshu.com/p/b0b19368e4a8

线上环境调整内容：
  1）数据库链接

  2）微信接口
     WeiXinController:accessTokenResult

微信SDK

https://github.com/wechat-group/weixin-java-tools


# 本地redis启动方法
     cd src
     ./redis-server ../redis.conf

# maven镜像设置为阿里
     在settings.xml中设置
     <mirrors>
         <mirror>
           <id>alimaven</id>
           <name>aliyun maven</name>
           <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
           <mirrorOf>central</mirrorOf>        
         </mirror>
       </mirrors>
       