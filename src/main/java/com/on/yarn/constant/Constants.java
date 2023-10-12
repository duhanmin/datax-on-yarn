package com.on.yarn.constant;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.LineHandler;
import cn.hutool.core.util.RuntimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * Constants
 */
@Slf4j
public class Constants {

    public static final String OSS = "oss://";

    public static final String S_3_A = "s3a://";

    public static final String S_3_N = "s3n://";

    public static final String S_3 = "s3://";

    public static final String SHELL_ARGS_PATH = "shellArgs";

    public static final String JAVA_OPTS_PATH = "javaOpts";

    public static final String JAR_FILE_LINKEDNAME = "jar";

    public static final String APP_MASTER_JAR_PATH = "AppMaster.jar";

    public static final String JAR_FILE_PATH = "JAR_FILE_PATH";

    public static final String LOG_4_J_PATH = "log4j.properties";

    public static final String DATAX = "datax";

    public static final String DATAX_HOME = "/" + DATAX + "/" + DATAX +"/";

    public static final String DATAX_JOB = "datax.job";

    public static final String DATAX_SCRIPT_PYTHON = "#!/bin/bash\n/usr/bin/python %s/bin/datax.py --jvm=\"-Xmx%dm\" %s";

    public static boolean exec(String command) {
        boolean result;
        Process process = null;
        InputStream inputStream = null;
        try {
            process = RuntimeUtil.exec(command);
            inputStream = process.getInputStream();
            IoUtil.readUtf8Lines(inputStream, (LineHandler) log::info);
            assert process.waitFor() == 0;
            result = true;
        } catch (Exception e) {
            log.error("process error" + command, e);
            result = false;
        } finally {
            RuntimeUtil.destroy(process);
            IoUtil.close(inputStream);
        }
        return result;
    }

}
