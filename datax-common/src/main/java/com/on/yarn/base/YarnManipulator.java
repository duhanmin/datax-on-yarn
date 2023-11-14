package com.on.yarn.base;

import cn.hutool.core.io.IoUtil;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;

/**
 * 实现操作接口
 */
public interface YarnManipulator {

    void info(String obj);

    void warn(String obj);

    void error(String obj);

    void error(String obj, Throwable t);

    /**
     * kill任务功能
     */
    default boolean killAppId(String appId) {
        YarnClient yarnClient = YarnClient.createYarnClient();
        try {
            yarnClient.init(new YarnConfiguration());
            yarnClient.start();
            ApplicationId applicationId = ApplicationId.fromString(appId);
            yarnClient.killApplication(applicationId);
            return true;
        } catch (Exception e) {
            error("yarn kill fail.", e);
        } finally {
            IoUtil.close(yarnClient);
        }
        return false;
    }
}
