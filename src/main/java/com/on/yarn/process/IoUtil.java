package com.on.yarn.process;

import lombok.NonNull;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Author: duhanmin
 * Description:
 * Date: 2021/7/6 11:34
 */
public class IoUtil {

    /**
     * 按行读取数据，针对每行的数据做处理
     *
     * @param in          {@link InputStream}
     * @param lineHandler 行处理接口，实现handle方法用于编辑一行的数据后入到指定地方
     * @throws IOException IO异常
     * @since 3.0.9
     */
    public static void readUtf8Lines(InputStream in, LineHandler lineHandler) throws IOException {
        readLines(getReader(in, StandardCharsets.UTF_8), lineHandler);
    }

    /**
     * 按行读取数据，针对每行的数据做处理<br>
     * {@link Reader}自带编码定义，因此读取数据的编码跟随其编码。
     *
     * @param reader      {@link Reader}
     * @param lineHandler 行处理接口，实现handle方法用于编辑一行的数据后入到指定地方
     * @throws IOException IO异常
     */
    public static void readLines(final @NonNull BufferedReader reader, @NonNull LineHandler lineHandler) throws IOException {
        // 从返回的内容中读取所需内容
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                lineHandler.handle(line);
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
    /**
     * 获得一个Reader
     *
     * @param in      输入流
     * @param charset 字符集
     * @return BufferedReader对象
     */
    public static BufferedReader getReader(InputStream in, Charset charset) {
        if (null == in) {
            return null;
        }

        InputStreamReader reader;
        if (null == charset) {
            reader = new InputStreamReader(in);
        } else {
            reader = new InputStreamReader(in, charset);
        }

        return new BufferedReader(reader);
    }

    /**
     * 关闭<br>
     * 关闭失败不会抛出异常
     *
     * @param closeable 被关闭的对象
     */
    public static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception e) {
                // 静默关闭
            }
        }
    }

    /**
     * 关闭<br>
     * 关闭失败不会抛出异常
     *
     * @param closeables 被关闭的对象
     */
    public static void close(Closeable... closeables) {
        if (null == closeables || closeables.length == 0) return;
        for (Closeable closeable:closeables) {
            close(closeable);
        }
    }

    public static void destroy(Process process) {
        if (null != process) {
            process.destroy();
        }
    }
}
