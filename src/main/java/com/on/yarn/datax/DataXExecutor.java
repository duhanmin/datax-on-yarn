package com.on.yarn.datax;

import com.on.yarn.constant.Constants;
import com.on.yarn.process.IoUtil;
import com.on.yarn.process.RuntimeUtil;
import com.on.yarn.util.FileUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

/**
 * Author: duhanmin
 * Description:
 * Date: 2021/6/30 17:45
 */
public class DataXExecutor {

    private static final Log LOG = LogFactory.getLog(DataXExecutor.class);

    private Process pro = null;
    private InputStream inputStream = null;
    private String sh = null;
    private String script = null;

    public Process init(int amMemory) throws Throwable {
        String path = new File("./").getAbsolutePath() + "/";
        String dataxJob = path + Constants.DATAX_JOB;
        String dataxHome = new File(path + Constants.DATAX_HOME).getAbsolutePath();
        script = String.format(Constants.DATAX_SCRIPT_PYTHON,dataxHome,amMemory,amMemory,dataxJob);
        LOG.info(script);
        sh = new File("./" + UUID.randomUUID() + ".sh").getAbsolutePath();
        FileUtil.writeUtf8String(sh,script);
        pro = RuntimeUtil.exec("sh",sh);
        return pro;
    }

    public void run() throws Throwable {
        try {
            inputStream = pro.getInputStream();
            IoUtil.readUtf8Lines(inputStream, System.out::println);
            int exitCode = pro.waitFor();
            if (exitCode != 0){
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
}
