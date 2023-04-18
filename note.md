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

## minio
* C:\minio.exe server D:\current_work\io\data1

## 什么情况下事务会失效？
![事务](https://raw.githubusercontent.com/wjlong1128/images/main/xczx/202304171126397.png)

## 断点续传是怎么做的？
![](https://raw.githubusercontent.com/wjlong1128/images/main/xczx/202304171128274.png)

## 视频转码
[FFmpeg](https://ffmpeg.org/download.html)  
[Github](https://github.com/FFmpeg/FFmpeg)  
