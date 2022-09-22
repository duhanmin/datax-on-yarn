# datax-on-yarn

datax-on-yarn可以让datax在yarn master上运行

* datax_home_hdfs datax在hdfs的安装包
* datax_job 配置json
* yarn master_memory内存为yarn master与datax job内存之和

```shell
/usr/bin/yarn jar /mnt/dss/211/datax-on-yarn-1.0.0.jar com.on.yarn.Client \
  -jar_path /mnt/dss/211/datax-on-yarn-1.0.0.jar \
  -appname datax-job \
  -master_memory 1024 \
  -p dt=20200324,pt=20200324 \
  -queue default \
  -proxy_user  hanmin.du \
  -datax_job /mnt/dss/datax/job/t2.json \
  -datax_home_hdfs /tmp/linkis/hadoop/datax.tar.gz
```


运行示例

![image](https://user-images.githubusercontent.com/28647031/181469603-e864c064-2b4c-4e0c-92d2-9cb9435435aa.png)
