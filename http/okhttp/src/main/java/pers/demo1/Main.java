package pers.demo1;

import okhttp3.*;

import java.io.IOException;

/**
 * 
 * @author cck
 */
public class Main {

    public static void main(String[] args) throws IOException {
        get();
        post();
    }

    // 声明媒体类型
    final static MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static void post() throws IOException {

        // 创建OkHttpClient实例
        OkHttpClient client = new OkHttpClient();

        // 构建请求体
        StringBuilder json = new StringBuilder();
        json.append("{")
                .append("\"name\"").append(":").append("\"cck\"").append(",")
                .append("\"password\"").append(":").append("\"1234567\"")
                .append("}");
        RequestBody requestBody = RequestBody.create(JSON, json.toString());

        // 创建请求
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/mfms/login")
                .post(requestBody)
                .build();
        // 发起请求
        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        }
    }

    private static void get() throws IOException {

        // 创建OkHttpClient实例
        OkHttpClient client = new OkHttpClient();

        // 创建请求
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .build();

        // 发起请求
        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        }
    }

}
