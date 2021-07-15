package com.on.yarn.datax;

import com.lb.bi.dcm.datax.yarn.constant.Constants;
import com.lb.bi.dcm.datax.yarn.process.IoUtil;
import com.lb.bi.dcm.datax.yarn.process.RuntimeUtil;
import com.lb.bi.dcm.datax.yarn.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Author: duhanmin
 * Description:
 * Date: 2021/6/30 17:45
 */
public class DataXExecutor {

    private static final Log LOG = LogFactory.getLog(DataXExecutor.class);

    private static void start(String dataxHome, String dataxJob,int amMemory) throws Throwable {
        String script = String.format(Constants.DATAX_SCRIPT_PYTHON,dataxHome,amMemory,amMemory,dataxJob);
        LOG.info(script);
        String sh = new File("./" + UUID.randomUUID() + ".sh").getAbsolutePath();
        FileUtil.writeUtf8String(sh,script);

        Process pro = null;
        InputStream inputStream = null;
        List<String> logs = new ArrayList<>();

        try {
            pro = RuntimeUtil.exec("sh",sh);
            inputStream = pro.getInputStream();
            IoUtil.readUtf8Lines(inputStream, line -> {
                if (StringUtils.isNotBlank(line))
                    logs.add(line.toLowerCase());
                System.out.println(line);
            });

            int exitCode = pro.waitFor();
            assert exitCode == 0;

            Integer mark = null;
            for (int i = 0; i < logs.size(); i++) {
                String logProcess = logs.get(i);
                if (null == mark){
                    if (logProcess.contains("info")){}
                    else if (logProcess.contains("warn")){}
                    else if (logProcess.contains("error"))
                        mark = i;
                }
            }

            if (null != mark){
                throw new RuntimeException();
            }else {
                LOG.info("job successfully :"+script);
            }

        }finally {
            IoUtil.close(inputStream);
            IoUtil.destroy(pro);
            FileUtil.del(sh);
        }
    }

    public static void run(int amMemory) throws Throwable {
        String path = new File("./").getAbsolutePath() + "/";
        String dataxJob = path + Constants.DATAX_JOB;
        String dataxHome = new File(path + Constants.DATAX_HOME).getAbsolutePath();
        DataXExecutor.start(dataxHome,dataxJob,amMemory);
    }

    public static void main(String[] args) throws Throwable {
        String home = "/Users/duhanmin/IdeaProjects/daima/DataX/target/datax/datax";
        String job = DataXExecutor.class.getResource("/").getFile() + "t1.json";
        DataXExecutor.start(home,job,512);
    }
}
