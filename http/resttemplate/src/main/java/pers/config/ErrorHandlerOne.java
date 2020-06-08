package pers.config;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * 也可继承  DefaultResponseErrorHandler
 */
public class ErrorHandlerOne implements ResponseErrorHandler {

    /**
     * 判断是否是错误的响应
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        System.out.println("method : hasError");
        return false;
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
