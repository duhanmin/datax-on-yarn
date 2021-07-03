package com.on.yarn.datax;

import com.alibaba.datax.common.exception.DataXException;
import com.alibaba.datax.common.spi.ErrorCode;
import com.alibaba.datax.core.Engine;
import com.alibaba.datax.core.util.FrameworkErrorCode;
/**
 * Author: duhanmin
 * Description:
 * Date: 2021/6/30 17:45
 */
public class DataXExecutor {

    public static void start(String dataxHome, String dataxJob) {
        int exitCode = 0;
        try {
            //设置运行的datax的目录
            System.setProperty("datax.home",dataxHome);
            //设置datax的运行脚本信息
            String[] args = new String[]{"-mode", "standalone", "-jobid", "-1", "-job",dataxJob};
            Engine.entry(args);
        } catch (Throwable e) {
            exitCode = 1;
            if (e instanceof DataXException) {
                DataXException tempException = (DataXException) e;
                ErrorCode errorCode = tempException.getErrorCode();
                if (errorCode instanceof FrameworkErrorCode) {
                    FrameworkErrorCode tempErrorCode = (FrameworkErrorCode) errorCode;
                    exitCode = tempErrorCode.toExitValue();
                }
            }
            e.printStackTrace();
            System.exit(exitCode);
        }
        System.exit(exitCode);
    }

    public static void main(String[] args) {
        String home = "/Users/duhanmin/Downloads/datax";
        String job = "//Users/duhanmin/IdeaProjects/daima/java-on-yarn/src/main/resources/t1.json";
        start(home,job);
    }
}
