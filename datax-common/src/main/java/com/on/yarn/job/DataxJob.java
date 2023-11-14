package com.on.yarn.job;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.io.File;

@Data
public class DataxJob {

    //@ApiModelProperty(name = "jarPath", value = "远程执行jar", dataType = "string", example = "s3://canary-lb-bi-presto-config/datax/datax-on-yarn-1.0.0.jar", required = true)
    private String jarPath = "/mnt/dss/datax-on-yarn-1.0.0.jar";

    //@ApiModelProperty(name = "appName", value = "任务名", dataType = "string", example = "datax_api_job")
    private String appName = "datax_api_job";

    //@ApiModelProperty(name = "memory", value = "执行内存", dataType = "long", example = "1024")
    private Long memory = 1024L;

    //@ApiModelProperty(name = "queue", value = "队列", dataType = "string", example = "default")
    private String queue = "default";

    //@ApiModelProperty(name = "job", value = "datax任务json", dataType = "object", example = "{}", required = true)
    private Object job;

    //@ApiModelProperty(name = "job", value = "datax任务json", dataType = "object", example = "{}", required = true)
    private File jobPath;

    //@ApiModelProperty(name = "dataxHome", value = "datax安装目录", dataType = "string", example = "/mnt/dss/datax")
    private String dataxHome = "/mnt/dss/datax";

    private boolean reflectRun = true;

    public String[] toStrinArray() {
        if (ObjectUtil.isNull(jobPath) && ObjectUtil.isNull(job)) {
            throw new IllegalArgumentException("jobPath or job is null");
        }
        if (ObjectUtil.isNull(jobPath) && ObjectUtil.isNotNull(job)) {
            File mkdir = FileUtil.mkdir("/tmp/datax-api/job/");
            jobPath = FileUtil.touch(mkdir.getAbsolutePath() + "/" + UUID.fastUUID().toString(true) + ".json");
            FileUtil.writeUtf8String(JSONUtil.toJsonStr(job), jobPath);
        }
        return new String[]{"-jar_path", jarPath, "-appname", appName, "-master_memory", memory.toString(), "-queue", queue, "-datax_job", jobPath.getAbsolutePath(), "-reflect_run", Boolean.toString(reflectRun), "-datax_home_hdfs", dataxHome};
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
