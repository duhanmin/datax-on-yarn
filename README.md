# datax-on-yarn

datax-on-yarn可以让datax在yarn master上运行

* datax_home_hdfs datax在hdfs的安装包
* datax_job 配置json
* master_memory内存为datax实际使用内存
* yarn master内存内置128m(一般不需要修改)

```shell
/usr/bin/yarn jar /mnt/dss/211/datax-on-yarn-1.0.0.jar com.on.yarn.Client \
  -jar_path /mnt/dss/211/datax-on-yarn-1.0.0.jar \
  -appname DemoApp \
  -master_memory 1024 \
  -queue default \
  -proxy_user  hanmin.du \
  -datax_job /mnt/dss/datax/job/t2.json \
  -datax_home_hdfs /tmp/linkis/hadoop/datax.tar.gz
```


运行示例

![image](https://user-images.githubusercontent.com/28647031/181469603-e864c064-2b4c-4e0c-92d2-9cb9435435aa.png)
