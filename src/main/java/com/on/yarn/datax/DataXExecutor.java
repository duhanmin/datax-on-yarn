package com.on.yarn.datax;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.LineHandler;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import com.on.yarn.constant.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * Author: duhanmin
 * Description:
 * Date: 2021/6/30 17:45
 */
@Slf4j
public class DataXExecutor {

    public static void start(String dataxHome, String dataxJob) throws Throwable {
        String script = StrUtil.format(Constants.DATAX_SCRIPT_PYTHON,dataxHome,dataxJob);
        Process pro = null;
        InputStream inputStream = null;
        try {
            pro = RuntimeUtil.exec(script);
            inputStream = pro.getInputStream();

            IoUtil.readUtf8Lines(inputStream, (LineHandler) line -> {
                System.out.println(line);
            });

            int exitCode = pro.waitFor();
            assert exitCode == 0;

            log.info("job运行结束:{}",script);
        }finally {
            IoUtil.close(inputStream);
            RuntimeUtil.destroy(pro);
        }
    }

    public static void main(String[] args) throws Throwable {
        String home = "/Users/duhanmin/Downloads/datax";
        String job = "/Users/duhanmin/IdeaProjects/daima/datax-on-yarn/src/main/resources/orcfile_none.json";
        start(home,job);
    }
}
