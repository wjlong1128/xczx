# 环境搭建

## 数据库
创建数据库`xczx-content`
![](https://raw.githubusercontent.com/wjlong1128/images/main/xczx/202304141436072.png)
导入文件sql

## nacos
* 安装`docker pull nacos/nacos-server:v2.2.2`
* 新建数据库 file/nacos-init.sql
* docker run -d  -e PREFER_HOST_MODE=hostname -e MODE=standalone -e JVM_XMS=256m  -e JVM_XMX=256m -e JVM_XMN=128m -p 8848:8848 -p 9848:9848 --name nacos nacos/nacos-server
* docker exec -it nacos bash
* 获取宿主ip `ping host.docker.internal`
![](https://raw.githubusercontent.com/wjlong1128/images/main/xczx/202304160908272.png)
* 8.0 添加时区`&serverTimezone=GMT%2B8`
* windows mysql8.0 url后拼上allowPublicKeyRetrieval=true

## minio
* C:\minio.exe server D:\current_work\io\data1

## 什么情况下事务会失效？
![事务](https://raw.githubusercontent.com/wjlong1128/images/main/xczx/202304171126397.png)

## 断点续传是怎么做的？
![断点续传](https://raw.githubusercontent.com/wjlong1128/images/main/xczx/202304171128274.png)

## 视频转码
[FFmpeg](https://ffmpeg.org/download.html)  
[Github](https://github.com/FFmpeg/FFmpeg)  

## xxl-job
![xxl-job执行流程](https://raw.githubusercontent.com/wjlong1128/images/main/xczx/202304180916384.png)
![多任务调度](https://raw.githubusercontent.com/wjlong1128/images/main/xczx/202304180918981.png)
![任务幂等性](https://raw.githubusercontent.com/wjlong1128/images/main/xczx/202304180919215.png)

## nginx
host 文件  
```text
127.0.0.1 www.51xuecheng.cn 51xuecheng.cn ucenter.51xuecheng.cn teacher.51xuecheng.cn file.51xuecheng.cn
```
nginx.conf
```text
http {
    include       mime.types;
    default_type  application/octet-stream;

    # 引入本项目的配置
    include xczx.conf;
```

xczx.conf
```text
server {
    listen       80;
    server_name  localhost;

    ssi on; # 页头页尾技术
    location / {
        root html/xc-ui-pc-static-portal;
        index  index.html index.htm;
    }

     location /static/img/ {
        alias  html/xc-ui-pc-static-portal/img/;
    }
    location /static/css/{
        alias html/xc-ui-pc-static-portal/css/;
    }
    location /static/js/ {
        alias html/xc-ui-pc-static-portal/js/;
    }

    location /static/plugins/ {
        alias html/xc-ui-pc-static-portal/plugins/;
        add_header Access-Control-Allow-Origin http://ucenter.51xuecheng.cn;
        add_header Access-Control-Allow-Credentials true;
        add_header Access-Control-Allow-Methods GET;
    }
    location /plugins/ {
           alias html/xc-ui-pc-static-portal/plugins/;
    }


    # 播放视频
    location /course/preview/learning.html {
         alias html/xc-ui-pc-static-portal/course/learning.html;
    }
    location /course/search.html {
            root   html/xc-ui-pc-static-portal;
    }
    location /course/learning.html {
            root   html/xc-ui-pc-static-portal;
    }

}

upstream fileserver{
        server localhost:9000 weight=10;
}

# 文件服务器
server {
    listen       80;
    server_name  file.51xuecheng.cn;

    ssi on;

    # 路径为minio的桶
    location /xczx-media {
        proxy_pass http://fileserver;
    }
}
```

## 分布式事务
![分布式事务是什么](https://raw.githubusercontent.com/wjlong1128/images/main/xczx/202304182113786.png)
![caid](https://raw.githubusercontent.com/wjlong1128/images/main/xczx/202304182115178.png)
[CAD](https://zhuanlan.zhihu.com/p/50990721)

![控制方案](https://raw.githubusercontent.com/wjlong1128/images/main/xczx/202304182133126.png)
![](https://raw.githubusercontent.com/wjlong1128/images/main/xczx/202304182134525.png)

