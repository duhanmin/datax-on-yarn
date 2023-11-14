# datax-on-yarn

datax-on-yarn可以让datax在yarn master上运行

* datax_home_hdfs datax在hdfs的安装包
* datax_job 配置json
* yarn master_memory内存为yarn master与datax job内存之和

## 提交

### shell方式

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

### sdk api方式(scala)

JobLogger类型重写com.on.yarn.base.YarnManipulator日志输出接口

```scala
val jobLogger = new JobLogger(job)
var client: Client = null
try {
  val cmd = dataxJob.toStrinArray
  jobLogger.info("------------------运行参数: " + ArrayUtil.toString(cmd))
  client = new Client(jobLogger)
  if (!client.init(cmd)) throw new RuntimeException("参数初始化异常: " + dataxJob)
  val applicationId: ApplicationId = client.run
  appId = applicationId.toString
  jobLogger.info("------------------DataX yarn id: " + applicationId.toString)
  val result = client.monitorApplication(applicationId)
  if (result) jobLogger.info("Application completed successfully")
  else throw new RuntimeException("任务运行异常,详见日志,AppID: " + applicationId)
} catch {
  case e: Exception => {
    jobLogger.info(ExceptionUtil.stacktraceToString(e))
  }
} finally {
  if (null != client) client.stop()
}

```

运行示例

![image](https://user-images.githubusercontent.com/28647031/181469603-e864c064-2b4c-4e0c-92d2-9cb9435435aa.png)
