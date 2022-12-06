package com.on.yarn.datax;

import cn.hutool.core.util.StrUtil;
import com.alibaba.datax.core.Engine;
import com.on.yarn.constant.Constants;

import java.io.File;

/**
 * Author: duhanmin
 * Description:
 * Date: 2021/6/30 17:45
 */
public class DataXExecutor implements Executor{

    private String path;

    @Override
    public void run() throws Throwable {
        String dataxHome = System.getProperty("datax");
        path = new File("./").getAbsolutePath() + "/";
        String dataxJob = path + Constants.DATAX_JOB;
        if (StrUtil.endWith(dataxHome,".tar.gz")){
            dataxHome = new File(path + Constants.DATAX_HOME).getAbsolutePath();
        }

        System.setProperty("datax.home", dataxHome);
        System.setProperty("logback.configurationFile", dataxHome + "/conf/logback.xml");
        System.setProperty("java.util.logging.config.file", dataxHome + "/conf/parquet-logging.properties");

        String[] args = new String[]{"-mode", "standalone", "-jobid", "-1", "-job", dataxJob};
        Engine.entry(args);
    }
}
