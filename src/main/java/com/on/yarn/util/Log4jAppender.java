package com.on.yarn.util;

import org.apache.commons.logging.Log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Author: duhanmin
 * Description:
 * Date: 2021/7/1 19:41
 */
public class Log4jAppender implements Log {

    private Class clazz;
    private String level;
    private Object message;
    private Throwable throwable;

    public Log4jAppender(Class clazz) {
        this.clazz = clazz;
    }

    private String log(){
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

        StringBuffer sb = new StringBuffer();
        sb.append(level).append(" ");
        sb.append(date).append(" ");
        sb.append(clazz.getName()).append(" ");
        if (null != message)
            sb.append(message).append(" ");
        if (null != throwable)
            sb.append(ExceptionUtils.stacktraceToOneLineString(throwable,300));
        return sb.toString();
    }

    private void parameter(String level,Object message, Throwable throwable) {
        this.level = level;
        this.message = message;
        if (null != throwable){
            this.throwable = throwable;
        }
        String log = log();
        System.out.println(log);
    }

    @Override
    public void info(Object message) {
        parameter("info",message,null);
    }

    @Override
    public void info(Object message, Throwable throwable) {
        parameter("info",message, throwable);
    }

    @Override
    public void warn(Object message) {
        parameter("warn",message,null);
    }

    @Override
    public void warn(Object message, Throwable throwable) {
        parameter("warn",message,throwable);
    }

    @Override
    public void error(Object message) {
        parameter("error",message,null);
    }

    @Override
    public void error(Object message, Throwable throwable) {
        parameter("error",message,throwable);
    }

    /**
     * 以下忽略
     */
    @Override
    public void fatal(Object message) {

    }

    @Override
    public void fatal(Object message, Throwable t) {

    }

    @Override
    public void trace(Object message) {

    }

    @Override
    public void trace(Object message, Throwable t) {

    }

    @Override
    public void debug(Object message) {

    }

    @Override
    public void debug(Object message, Throwable t) {

    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public boolean isFatalEnabled() {
        return false;
    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }
}
