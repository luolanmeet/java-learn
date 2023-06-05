package pers.demo1;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @auther ken.ck
 * @date 2023/6/2 15:06
 */
public class Test {

    public static void main(String[] args) throws Exception {

        OkHttpClient client = OkHttpConfig.getOkHttpClient();

        int i = 0;

        while (true) {
            Thread.sleep(1000);

            // 创建请求
            Request request = new Request.Builder()
                    .url("http://localhost:8080/hello" + "/" + i++)
                    .build();
            // 发起请求
            try (Response response = client.newCall(request).execute()) {
                System.out.println(response.body().string());
            }
        }
    }

}
