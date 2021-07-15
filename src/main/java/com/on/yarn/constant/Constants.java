package com.on.yarn.constant;

/**
 * Constants
 */
public class Constants {

    public static final String SHELL_ARGS_PATH = "shellArgs";

    public static final String JAVA_OPTS_PATH = "javaOpts";

    public static final String JAR_FILE_LINKEDNAME = "jar";

    public static final String APP_MASTER_JAR_PATH = "AppMaster.jar";

    public static final String JAR_FILE_PATH = "JAR_FILE_PATH";

    public static final String LOG_4_J_PATH = "log4j.properties";

    public static final String DATAX = "datax";

    public static final String DATAX_HOME = "/" + DATAX + "/" + DATAX +"/";

    public static final String DATAX_JOB = "datax.job";

    public static final String DATAX_SCRIPT_PYTHON = "#!/bin/bash\n/usr/bin/python %s/bin/datax.py --jvm=\"-Xms%dm -Xmx%dm\" %s";

}
