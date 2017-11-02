# spring-generator
一个用于生成Spring项目的东西，包含web.xml，根上下文和web上下文，以及Hibernate的基本配置。

## 基本使用
在[此网址](http://ok34fi9ya.bkt.clouddn.com/spring-generator-0.0.2.zip)下载压缩包，解压。

编辑settings.json文件，其中``group_id, artifact_id, version``字段为必填，其余之后会有详细的配置。

终端切换到文件夹，执行``java -jar spring-generator-0.0.2.jar``即可。

将在目录中生成的文件夹用Idea导入，类型为Maven Project。

## 全部配置参数
除了上面所说的必填字段之外，还有很多选填字段，选填的配置字段如下：

|字段|说明|
|---|---|
|project|项目的名称，默认为artifact_id|
|local_repo|maven本地Repository地址，在不指定依赖版本的情况下先去此文件夹下查找依赖版本，找不到则使用默认版本|
|spring_version|指定Spring的版本，默认是4.3.12.RELEASE|
|j2ee_version|指定Java EE版本，默认为7.0|
|servlet_version|指定Servlet的版本，默认为3.1.0|
|jedis_version|指定Jedis的版本，默认为2.9.0|
|junit_version|指定JUnit版本，默认为4.12|
|jackson2_version|指定Jackson2的版本，默认为2.8.10|
|hibernate_version|指定Hibernate的版本，默认5.2.10.Final|
|jndi|指定容器中的JNDI，用于配置MySQL数据库|

全部字段的类型都为字符串。
