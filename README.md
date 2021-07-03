# datax-on-yarn

该项目可以让datax在yarn master上运行

* datax_home_hdfs 为datax在hdfs的安装包
* datax_job为所用运行配置json

其他参数略


```shell

yarn jar /code/datax-on-yarn-1.0.0.jar com.on.yarn.Client \
  -jar_path /code/datax-on-yarn-1.0.0.jar \
  -appname DemoApp \
  -master_memory 1024 \
  -queue default \
  -datax_job /code/orcfile_none.json \
  -datax_home_hdfs /datax/datax.tar.gz
```
