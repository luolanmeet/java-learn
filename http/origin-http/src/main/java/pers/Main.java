package pers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author cck
 */
public class Main {

    public static void main(String[] args) throws IOException {
        get();
        post();
    }

    private static void post() throws IOException {

        URL url = new URL("http://localhost:8080/api/mfms/login");
        // 打开和URL之间的连接
        URLConnection connection = url.openConnection();

        // 设置通用的请求属性
        connection.setRequestProperty("Content-Type", "application/json");

        // 发送POST请求必须设置如下两行
        connection.setDoOutput(true);
        connection.setDoInput(true);
        // 获取URLConnection对象对应的输出流
        PrintWriter out = new PrintWriter(connection.getOutputStream());
        // 发送请求参数
        StringBuffer params = new StringBuffer();
        params.append("{")
                    .append("\"name\"").append(":").append("\"cck\"").append(",")
                    .append("\"password\"").append(":").append("\"1234567\"")
                .append("}");

        out.print(params.toString());
        out.flush();

        // 定义BufferedReader输入流来读取URL的响应
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());

        if (bufferedReader != null) {
            bufferedReader.close();
        }
    }

    /**
     * GET 请求
     * @throws IOException
     */
    private static void get() throws IOException {

        URL url = new URL("http://www.baidu.com");
        // 打开和URL之间的连接
        URLConnection connection = url.openConnection();
        // 设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");

        // 建立实际的连接
        connection.connect();

        // 获取所有响应头字段
        Map<String, List<String>> headerFields = connection.getHeaderFields();
        // 遍历所有响应头字段
        for (Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        // 定义BufferReader输入流读取响应
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder responseBody = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            responseBody.append(line);
        }

        System.out.println();
        System.out.println(responseBody.toString());

        if (bufferedReader != null) {
            bufferedReader.close();
        }

    }

}
