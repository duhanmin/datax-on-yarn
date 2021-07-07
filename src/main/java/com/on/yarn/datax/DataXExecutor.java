package com.on.yarn.datax;

import com.on.yarn.constant.Constants;
import com.on.yarn.process.IoUtil;
import com.on.yarn.process.RuntimeUtil;
import com.on.yarn.util.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: duhanmin
 * Description:
 * Date: 2021/6/30 17:45
 */
@Slf4j
public class DataXExecutor {


    private static void start(String dataxHome, String dataxJob) throws Throwable {
        String script = String.format(Constants.DATAX_SCRIPT_PYTHON,dataxHome,dataxJob);
        Process pro = null;
        InputStream inputStream = null;
        List<String> logs = new ArrayList<>();

        try {
            pro = RuntimeUtil.exec(script);
            inputStream = pro.getInputStream();
            IoUtil.readUtf8Lines(inputStream, line -> {
                if (CharSequenceUtil.isNotBlank(line))
                    logs.add(line.toLowerCase());
                System.out.println(line);
            });

            int exitCode = pro.waitFor();
            assert exitCode == 0;

            Integer mark = null;
            for (int i = 0; i < logs.size(); i++) {
                String logProcess = logs.get(i);
                if (null == mark && logProcess.contains("error")){
                    mark = i;
                }
            }

            if (null != mark){
                throw new RuntimeException();
            }else {
                log.info("job successfully :{}",script);
            }

        }finally {
            IoUtil.close(inputStream);
            IoUtil.destroy(pro);
        }
    }

    public static void run() throws Throwable {
        String path = new File("./").getAbsolutePath() + "/";
        String dataxJob = path + Constants.DATAX_JOB;
        String dataxHome = new File(path + Constants.DATAX_HOME).getAbsolutePath();
        DataXExecutor.start(dataxHome,dataxJob);
    }

    public static void main(String[] args) throws Throwable {
        String home = "/Users/duhanmin/Downloads/datax";
        String job = DataXExecutor.class.getResource("/").getFile() + "t1.json";
        start(home,job);
    }
}
