package com.on.yarn.datax;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.on.yarn.constant.Constants;

public interface Executor {
    void run() throws Throwable;

    default void init(String dataxHome, String path) {
        if (!FileUtil.exist(dataxHome)) {
            String cmd = "mkdir -p /mnt/dss && cd /mnt/dss && hdfs dfs -get -f /tmp/linkis/hadoop/datax/datax.tar.gz . && tar -zxf datax.tar.gz";
            String cmdPath = path + "init_cmd.sh";
            FileUtil.writeUtf8String(cmd, cmdPath);
            Constants.exec("sh " + cmdPath);
            FileUtil.del(cmdPath);
        }
    }

    default void end(String path) {
        try {
            String log = FileUtil.readUtf8String(path + "log.log");
            HttpUtil.post(System.getProperty("log"), log, 30000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
