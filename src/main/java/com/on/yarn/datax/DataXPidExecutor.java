package com.on.yarn.datax;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.LineHandler;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import com.on.yarn.constant.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
public class DataXPidExecutor implements Executor{

    private String path;
    private Process pro = null;
    private InputStream inputStream = null;
    private String sh = null;
    private String script = null;

    public DataXPidExecutor(int amMemory) {
        path = new File("./").getAbsolutePath() + "/";
        String dataxJob = path + Constants.DATAX_JOB;
        String dataxHome = System.getProperty("datax");
        if (StrUtil.endWith(dataxHome,".tar.gz")){
            dataxHome = new File(path + Constants.DATAX_HOME).getAbsolutePath();
        }
        script = String.format(Constants.DATAX_SCRIPT_PYTHON,dataxHome,amMemory,amMemory,dataxJob);
        log.info(script);
        sh = path + UUID.randomUUID() + ".sh";
        log.info(sh);
        FileUtil.writeUtf8String(sh,script);
        pro = RuntimeUtil.exec("sh",sh);
    }

    @Override
    public void run() throws Throwable {
        try {
            inputStream = pro.getInputStream();
            IoUtil.readUtf8Lines(inputStream, (LineHandler) System.out::println);
            int exitCode = pro.waitFor();
            if (exitCode != 0){
                throw new RuntimeException();
            }else {
                log.info("job successfully :"+script);
            }
        }finally {
            IoUtil.close(inputStream);
            RuntimeUtil.destroy(pro);
            FileUtil.del(sh);
        }
    }

    @Override
    public void successful() {
        Constants.exec("ls -la");
    }

    @Override
    public void failure(){
        Constants.exec("ls -la");
    }

}
