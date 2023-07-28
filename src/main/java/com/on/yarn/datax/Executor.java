package com.on.yarn.datax;

import cn.hutool.core.io.FileUtil;
import com.on.yarn.constant.Constants;

public interface Executor {

    default String getLog(String path) {
        return FileUtil.readUtf8String(path + "log.log");
    }

    String getLog();

    void run() throws Throwable;

    default void init(String dataxHome, String path) {
        if (!FileUtil.exist(dataxHome)) {
            String cmd = "sudo mkdir -p /mnt/dss && sudo chmod 777 /mnt/dss && cd /mnt/dss && hdfs dfs -get -f /tmp/linkis/hadoop/datax/datax.tar.gz . && tar -zxf datax.tar.gz";
            String cmdPath = path + "init_cmd.sh";
            FileUtil.writeUtf8String(cmd, cmdPath);
            Constants.exec("sh " + cmdPath);
            FileUtil.del(cmdPath);
        }
    }
}
