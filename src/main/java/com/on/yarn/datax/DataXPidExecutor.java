package com.on.yarn.datax;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.LineHandler;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import com.on.yarn.constant.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
public class DataXPidExecutor implements Executor {

    private String path;
    private Process pro;
    private InputStream inputStream = null;
    private String sh;
    private String script;

    public DataXPidExecutor(int amMemory) throws IOException {
        path = new File("./").getAbsolutePath() + "/";
        String dataxJob = path + Constants.DATAX_JOB;
        String dataxHome = System.getProperty("datax");
        if (StrUtil.endWith(dataxHome, ".tar.gz")) {
            dataxHome = new File(path + Constants.DATAX_HOME).getAbsolutePath();
        }
        script = String.format(Constants.DATAX_SCRIPT_PYTHON, dataxHome, amMemory, dataxJob);
        log.info(script);
        sh = new File(path + UUID.randomUUID() + ".sh").getAbsolutePath();
        log.info(sh);
        FileUtil.writeUtf8String(script, sh);
        pro = Runtime.getRuntime().exec("sh " + sh);
    }

    @Override
    public void run() throws Throwable {
        try {
            inputStream = pro.getInputStream();
            IoUtil.readUtf8Lines(inputStream, (LineHandler) log::info);
            int exitCode = pro.waitFor();
            if (exitCode != 0){
                throw new RuntimeException();
            }else {
                log.info("job successfully :"+script);
            }
        }finally {
            end(path);
            IoUtil.close(inputStream);
            RuntimeUtil.destroy(pro);
            FileUtil.del(sh);
        }
    }
}
