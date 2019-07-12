package org.kangspace.springcloud.graphql.core.config.db;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 从DynamicDataSource 的定义看出，他返回的是DynamicDataSourceHolder.getDataSouce()值，
 * 我们需要在程序运行时调用 DynamicDataSourceHolder.putDataSource()方法，对其赋值。
 * 下面是我们实现的核心部分，也就是AOP部分，DataSourceAspect定义如下:
 */
@Component
@Aspect
@Order(10)
public class DataSourceAspectHandle {

    @Before("execution(* org.kangspace.springcloud.dtxs.caller.service..*(..)) ")
    public void before(JoinPoint point) {
        Object target = point.getTarget();
        String method = point.getSignature().getName();

        Class<?>[] classz = target.getClass().getInterfaces();

        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature())
                .getMethod().getParameterTypes();
        try {
            Method m = classz[0].getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource data = m.getAnnotation(DataSource.class);
                DbContextHolder.contextHolder.set(data.value());
            } else {
                // 默认数据库
                DbContextHolder.contextHolder.set(DataSourceType.MASTER);
            }
            System.out.println("DB_TYPE:"+DbContextHolder.contextHolder.get());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Pointcut("execution(* org.kangspace.springcloud.dtxs.caller.service..*(..)) ")
    public void dataSourcePointcut() {
    }

//    @Around("dataSourcePointcut()")
//    public Object doAround(ProceedingJoinPoint pjp) {
//        System.out.println(("doAround：--- "));
//        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
//        Method method = methodSignature.getMethod();
//        if (method != null && method.isAnnotationPresent(DataSource.class)) {
//            DataSource data = method.getAnnotation(DataSource.class);
//            DbContextHolder.setDbType(data.value());
//        } else {
//            DbContextHolder.setDbType(DataSourceType.MASTER);//没有注解默认主库
//        }
//        Object result = null;
//        try {
//            result = pjp.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        } finally {
//            DbContextHolder.clearDbType();
//        }
//        return result;
//    }
}
