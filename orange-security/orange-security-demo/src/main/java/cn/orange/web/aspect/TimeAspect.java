package cn.orange.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author : kz
 * @date : 2019/7/22
 */
@Aspect
@Component
public class TimeAspect {

    @Pointcut("execution(public * cn.orange.web.controller.UserController.*(..))")
    private void anyPublicOperation() {}

    @Pointcut("execution(* cn.orange.web.controller.UserController.*(..))")
    private void anyUserController() {}

    @Around("anyPublicOperation()")
    public Object around(ProceedingJoinPoint pjp) {
        Long t1 = System.currentTimeMillis();
        Object proceed = null;
        try {
            proceed = pjp.proceed();
            System.out.println("proceed = " + proceed);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        Long time = System.currentTimeMillis() - t1;
        System.out.println("time = " + time);
        return proceed;
    }


}
