package com.on.yarn.process;

/**
 * 行处理器
 *
 * Author: duhanmin
 * Description:
 * Date: 2021/7/6 11:38
 */
@FunctionalInterface
public interface LineHandler {
    /**
     * 处理一行数据，可以编辑后存入指定地方
     * @param line 行
     */
    void handle(String line);
}
