### 如何使用
```
$ 运行sql

$ mvn clean compile

$ mvn clean package

$ mvn clean install

$ mvn jetty:run

```
### 说明
1. 如果使用该项目出现问题，请联系zzp

```
### 附上aliyun.properties结构
!oss客户端实例的beanname
oss_client=ossClient
!oss区域id
oss_regionId=***
!oss位置
oss_endpoint=***
!ossRAM的账号
oss_accessKeyId=***
oss_accessKeySecret=***
!oss读权限的ROLE
oss_roleArn=***
!--token的存活时间 /秒
token_timeout=3600
!--token缓存的自动检查时间 15min /ms  15min=900 000
token_period=60000
!各个bucket的名称
db_author=***
db_poem=***
db_poemshow=***
db_test=***

!OSSClient的配置参数
!允许打开的最大HTTP连接数。默认为1024
MaxConnections=1024
!Socket层传输数据的超时时间（单位：毫秒）。默认为50000毫秒
SocketTimeout=50000
!建立连接的超时时间（单位：毫秒）。默认为50000毫秒
ConnectionTimeout=50000
!从连接池中获取连接的超时时间（单位：毫秒）。默认不超时
!ConnectionRequestTimeout=500
!连接空闲超时时间，超时则关闭连接（单位：毫秒，默认为60000毫秒
IdleConnectionTime=10000
!设置失败请求重试次数，默认为3次
MaxErrorRetry=3
!连接OSS所采用的协议（HTTP/HTTPS），默认为HTTP
Protocol=HTTP

