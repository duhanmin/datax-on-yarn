package com.on.yarn.process;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Author: duhanmin
 * Description:
 * Date: 2021/7/6 15:49
 */
public class RuntimeUtil {
    /**
     * 执行命令<br>
     * 命令带参数时参数可作为其中一个参数，也可以将命令和参数组合为一个字符串传入
     *
     * @param cmds 命令
     * @return {@link Process}
     */
    public static Process exec(String... cmds) throws IOException {
        Process process;
        try {
            process = new ProcessBuilder(handleCmds(cmds)).redirectErrorStream(true).start();
        } catch (IOException e) {
            throw new IOException(e);
        }
        return process;
    }

    /**
     * 处理命令，多行命令原样返回，单行命令拆分处理
     * @param cmds 命令
     * @return 处理后的命令
     */
    private static String[] handleCmds(String... cmds){
        if (cmds == null || cmds.length == 0) {
            throw new NullPointerException("Command is empty !");
        }

        // 单条命令的情况
        if (1 == cmds.length) {
            final String cmd = cmds[0];
            if (StringUtils.isBlank(cmd)) {
                throw new NullPointerException("Command is blank !");
            }
            cmds = cmdSplit(cmd);
        }
        return cmds;
    }

    /**
     * 命令分割，使用空格分割，考虑双引号和单引号的情况
     *
     * @param cmd 命令，如 git commit -m 'test commit'
     * @return 分割后的命令
     */
    private static String[] cmdSplit(String cmd){
        final List<String> cmds = new ArrayList<>();

        final int length = cmd.length();
        final Stack<Character> stack = new Stack<>();
        boolean inWrap = false;
        final StringBuffer cache = new StringBuffer();

        char c;
        for (int i = 0; i < length; i++) {
            c = cmd.charAt(i);
            switch (c){
                case SINGLE_QUOTE:
                case DOUBLE_QUOTES:
                    if(inWrap){
                        if(c == stack.peek()){
                            //结束包装
                            stack.pop();
                            inWrap = false;
                        }
                        cache.append(c);
                    } else{
                        stack.push(c);
                        cache.append(c);
                        inWrap = true;
                    }
                    break;
                case SPACE:
                    if(inWrap){
                        // 处于包装内
                        cache.append(c);
                    } else{
                        cmds.add(cache.toString());
                        cache.delete(0,cache.length());
                    }
                    break;
                default:
                    cache.append(c);
                    break;
            }
        }

        if(cache.length() > 0){
            cmds.add(cache.toString());
        }

        return cmds.toArray(new String[0]);
    }

    private static final char DOUBLE_QUOTES = '"';
    private static final char SINGLE_QUOTE = '\'';
    private static final char SPACE = ' ';
}
