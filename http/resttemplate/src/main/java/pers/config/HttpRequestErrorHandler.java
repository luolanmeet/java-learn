package pers.config;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

/**
 * 也可实现 ResponseErrorHandler 接口
 * ResponseErrorHandler 只能才处理请求之后，响应不是成功的场景
 * 像程序出现异常是不会进入这里的流程的
 */
public class HttpRequestErrorHandler extends DefaultResponseErrorHandler {

    /**
     * 判断是否是错误的响应
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        System.out.println("method : hasError");
        return super.hasError(response);
    }

    /**
     * 处理异常
     * @param response
     * @throws IOException
     */
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        System.out.println("method : handleError");
    }

}
