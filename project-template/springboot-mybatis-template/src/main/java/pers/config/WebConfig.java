package pers.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pers.common.TraceTool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @auther ken.ck
 * @date 2023/7/7 17:29
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // trace id 设置
        registry.addInterceptor(new HandlerInterceptor() {
                    @Override
                    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                        String traceId = request.getHeader(TraceTool.TRACE_ID);
                        if (StringUtils.isEmpty(traceId)) {
                            TraceTool.genTraceId();
                        } else {
                            TraceTool.setTraceId(traceId);
                        }
                        return true;
                    }
                    @Override
                    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
                        TraceTool.removeTraceId();
                    }
                })
                .addPathPatterns("/**").order(Integer.MIN_VALUE);
    }

}
