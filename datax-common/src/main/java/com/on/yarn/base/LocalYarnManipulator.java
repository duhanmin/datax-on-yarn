package com.on.yarn.base;

import lombok.extern.slf4j.Slf4j;

/**
 * 本地shell提交操作类
 */
@Slf4j
public class LocalYarnManipulator implements YarnManipulator {

    @Override
    public void info(String obj) {
        log.info(obj);
    }

    @Override
    public void warn(String obj) {
        log.warn(obj);
    }

    @Override
    public void error(String obj) {
        log.error(obj);
    }

    @Override
    public void error(String obj, Throwable t) {
        log.error(obj, t);
    }
}
