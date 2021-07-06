package com.on.yarn.datax;

import com.on.yarn.constant.Constants;
import com.on.yarn.process.IoUtil;
import com.on.yarn.process.RuntimeUtil;
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
        String script = String.format(Constants.DATAX_SCRIPT_PYTHON,dataxHome,dataxJob);
        Process pro = null;
        InputStream inputStream = null;
        try {
            pro = RuntimeUtil.exec(script);
            inputStream = pro.getInputStream();
            IoUtil.readUtf8Lines(inputStream, System.out::println);

            int exitCode = pro.waitFor();
            assert exitCode == 0;

            log.info("job运行结束:{}",script);
        }finally {
            IoUtil.close(inputStream);
            IoUtil.destroy(pro);
        }
    }

    public static void main(String[] args) throws Throwable {
        String home = "/Users/duhanmin/Downloads/datax";
        String job = DataXExecutor.class.getResource("/").getFile() + "t1.json";
        start(home,job);
    }
}
