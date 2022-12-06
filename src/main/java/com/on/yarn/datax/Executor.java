package com.on.yarn.datax;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;

public interface Executor {

    void run() throws Throwable;

    default void end(String path) {
        String log = FileUtil.readUtf8String(path + "log.log");
        HttpUtil.post(System.getProperty("log"), log, 30000);
    }
}
