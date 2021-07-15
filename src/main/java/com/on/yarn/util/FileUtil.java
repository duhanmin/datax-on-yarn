package com.on.yarn.util;

import com.on.yarn.process.IoUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Author: duhanmin
 * Description:
 * Date: 2021/7/14 17:30
 */
public class FileUtil {

    /**
     * 数据写入文件
     * @param filePath
     * @param content
     */
    public static File writeUtf8String(String filePath,String content) throws IOException {
        File file;
        FileOutputStream writerStream = null;
        BufferedWriter bufferedWriter = null;

        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            writerStream = new FileOutputStream(filePath);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(writerStream, StandardCharsets.UTF_8));
            bufferedWriter.write(content);
        }finally {
            IoUtil.close(bufferedWriter,writerStream);
        }

        return file;
    }

    /**
     * 删除文件
     * @param filePath
     * @throws IOException
     */
    public static void del(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }
}
