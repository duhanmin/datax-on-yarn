package com.on.yarn.datax;

import com.alibaba.datax.core.Engine;
import com.on.yarn.constant.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;

/**
 * Author: duhanmin
 * Description:
 * Date: 2021/6/30 17:45
 */
public class DataXExecutor {

    private static final Log LOG = LogFactory.getLog(DataXExecutor.class);
    static {
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("logback.statusListenerClass", "ch.qos.logback.core.status.NopStatusListener");
        System.setProperty("java.security.egd", "file:///dev/urandom");
    }

    public void entry() throws Throwable {
        String path = new File("./").getAbsolutePath() + "/";
        String dataxJob = path + Constants.DATAX_JOB;
        String dataxHome = new File(path + Constants.DATAX_HOME).getAbsolutePath();

        System.setProperty("datax.home", dataxHome);
        System.setProperty("logback.configurationFile", dataxHome + "/conf/logback.xml");
        System.setProperty("java.util.logging.config.file", dataxHome + "/conf/parquet-logging.properties");

        String[] args = new String[]{"-mode", "standalone", "-jobid", "-1", "-job", dataxJob};
        Engine.entry(args);
    }
}
