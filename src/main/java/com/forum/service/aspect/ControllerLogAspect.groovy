package com.forum.service.aspect

import com.alibaba.fastjson.JSONObject
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper
import com.forum.model.entity.LogEntity
import com.forum.utils.CommonUtil
import com.forum.utils.ShiroUtil;
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.text.SimpleDateFormat;
/**
 * @describe: 实现controller的日志切面
 * @author: zhuchunwang
 * @date: 2018/5/29 17:40
 * @version: 1.0
 */
@Aspect
@Component
@Order(1)
class ControllerLogAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    ThreadLocal<Long> startTime = new ThreadLocal<>();
    ThreadLocal<LogEntity> webLogThreadLocal = new ThreadLocal<>();
    /**
     * 定义一个切入点.
     * 解释下：
     * <p>
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 任意包名
     * ~ 第三个 * 代表任意方法.
     * ~ 第四个 * 定义在web包或者子包
     * ~ 第五个 * 任意方法
     * ~ .. 匹配任意数量的参数.
     */
    @Pointcut("execution(* com.forum.controller.*.*(..))")
    public void serviceAspect() {
    }
    @Before("serviceAspect()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        webLogThreadLocal.set(new LogEntity());
        webLogThreadLocal.get().setHttpMethod(request.getMethod());
        webLogThreadLocal.get().setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        webLogThreadLocal.get().setUrl(request.getRequestURL().toString());
        webLogThreadLocal.get().setIp(request.getRemoteAddr());
        webLogThreadLocal.get().setArgs(CommonUtil.getArgs(joinPoint));
        webLogThreadLocal.get().setLogType('');
        webLogThreadLocal.get().setUser(ShiroUtil.getUser());
        logger.error('Date : '+(new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss").format(new Date())))
//        logger.error('Request Method : '+request.getMethod())
//        logger.error('Class Method : '+joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//        logger.error('Request URL : '+request.getRequestURL().toString());
//        logger.error('Real IP : '+CommonUtil.getRealIP(request));
//        logger.error('Args : '+CommonUtil.getArgs(joinPoint));
        logger.error('Request Parameter : '+(request.getParameterMap()==null?'Unknow Parameter':JSONObject.toJSON(request.getParameterMap())));
        logger.error('Username : '+ShiroUtil.getUser()?.getUsername());
    }

//    @Around("serviceAspect()")
//    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        try {
//            long startTime = System.currentTimeMillis();
//            Object ob= proceedingJoinPoint.proceed();
//            logger.error("Time : " + (System.currentTimeMillis() - startTime));
//            return ob;
//        } catch (Exception e) {
//            logger.error("error:",e);
//        }
//
//    }
    /**
     * 异常通知 用于拦截service层记录异常日志
     *
     * @param
     * @param
     */
    @AfterReturning(returning = "result", pointcut = "serviceAspect()")
    public void doAfterReturning(Object result) {
        // 处理完请求，返回内容
        ObjectMapper mapper = new ObjectMapper();
        try {
            logger.error("response={}",result.toString());
            webLogThreadLocal.get().setRespParams(mapper.writeValueAsString(result));
        } catch (JsonProcessingException e) {
            logger.error(CommonUtil.getExceptionDetail(e));
        }
        webLogThreadLocal.get().setSpendTime(System.currentTimeMillis() - startTime.get());
        try {
            logger.error(">>>"+mapper.writeValueAsString(webLogThreadLocal.get()));
        } catch (JsonProcessingException e) {
            logger.error(CommonUtil.getExceptionDetail(e));
        }
    }
}
