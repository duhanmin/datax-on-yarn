package com.on.yarn;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.server.HttpServerResponse;
import cn.hutool.http.server.SimpleServer;

import java.net.InetSocketAddress;

public class ClientServer {

    private static final String LOG = "http://{}:{}/log";
    private SimpleServer server;
    /**
     * 初始化Client接口
     *
     * @return
     */
    public void initClient() {
        server = HttpUtil.createServer(0);
        server.addAction("/log", (request, response) -> {
            System.out.println("[AppMasterLog] " + request.getBody());
            responseWriteSuccess(response);
        }).start();
    }

    public String getUrl(){
        InetSocketAddress inetSocketAddress = server.getAddress();
        return StrUtil.format(LOG,ip(),inetSocketAddress.getPort());
    }



    public static void responseWriteSuccess(HttpServerResponse response) {
        responseWrite(response, "{\"status\": 200}");
    }

    private static void responseWrite(HttpServerResponse response, String data) {
        response.write(data, ContentType.JSON.toString());
    }

    public static String ip() {
        for (String localIp : NetUtil.localIps()) {
            if (localIp.startsWith("10.")) {
                return localIp;
            }
        }
        return "";
    }
}
