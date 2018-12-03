package com.forum.controller

import com.alibaba.fastjson.JSONObject
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper
import com.forum.model.entity.LogEntity
import com.forum.utils.CommonUtil
import com.forum.utils.ShiroUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse;
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
        webLogThreadLocal.get().setReqParams(request.getParameterMap()?.size()==0?'':JSONObject.toJSON(request.getParameterMap()));
        webLogThreadLocal.get().setUser(ShiroUtil.getUser());
    }
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
            webLogThreadLocal.get().setRespParams(mapper.writeValueAsString(result));
        } catch (JsonProcessingException e) {
            logger.error(CommonUtil.getExceptionDetail(e));
        }
        webLogThreadLocal.get().setSpendTime(System.currentTimeMillis() - startTime.get());
        try {
            logger.info(">>>"+mapper.writeValueAsString(webLogThreadLocal.get()));
        } catch (JsonProcessingException e) {
            logger.error(CommonUtil.getExceptionDetail(e));
        }
    }
}
