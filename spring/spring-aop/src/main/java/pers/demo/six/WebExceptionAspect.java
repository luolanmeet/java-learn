package pers.demo.six;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * controller接口异常处理，直接切 RequestMapping 注解
 * @auther ken.ck
 * @date 2021/7/17 10:33
 */
@Aspect
public class WebExceptionAspect {

    /** 点切面 */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void webPointcut() {}

    /** 处理异常 */
    @AfterThrowing(pointcut = "webPointcut()", throwing = "e")
    public void handleException(Exception e) {
        // LOG
    }

}
