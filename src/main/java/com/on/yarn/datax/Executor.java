package com.on.yarn.datax;

public interface Executor {
    void run() throws Throwable;
    void successful();
    void failure();
}
