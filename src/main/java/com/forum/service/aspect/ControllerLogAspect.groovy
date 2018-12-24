package com.forum.service.aspect

import com.alibaba.fastjson.JSONObject
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.forum.model.entity.LogEntity
import com.forum.utils.CommonUtil
import com.forum.utils.ShiroUtil
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

import javax.servlet.http.HttpServletRequest
import java.text.SimpleDateFormat

/**
 * @describe: 实现controller的日志切面
 * @author: LJL
 * @date: 2018/5/29 17:40
 * @version: 1.0
 */
@Aspect
@Component
@Order(1)
class ControllerLogAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass())
    ThreadLocal<Long> startTime = new ThreadLocal<>()
    ThreadLocal<LogEntity> webLogThreadLocal = new ThreadLocal<>()
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
    void serviceAspect() {
    }

    @Before("serviceAspect()")
    void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis())
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes()
        HttpServletRequest request = attributes.getRequest()
        webLogThreadLocal.set(new LogEntity())
        webLogThreadLocal.get().setHttpMethod(request.getMethod())
        webLogThreadLocal.get().setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName())
        webLogThreadLocal.get().setUrl(request.getRequestURL().toString())
        webLogThreadLocal.get().setIp(CommonUtil.getRealIP(request))
        webLogThreadLocal.get().setArgs(CommonUtil.getArgs(joinPoint))
        webLogThreadLocal.get().setLogType('isJsonRequest:' + CommonUtil.isJsonRequest(request))
        webLogThreadLocal.get().setUser(ShiroUtil.getUser())
        logger.info('Date : ' + (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())))
        logger.info('Request Parameter : ' + (CommonUtil.isEmpty(request.getParameterMap()) ? 'Empty Parameter' : JSONObject.toJSON(request.getParameterMap())))
        logger.info('Username : ' + (ShiroUtil.getUser()?.getUsername() ?: '未登录'))
    }

    @Around("serviceAspect()")
    Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis()
        Object ob = proceedingJoinPoint.proceed()
        logger.info("Finish Method Spend Time : ".concat((System.currentTimeMillis() - startTime).toString()).concat('ms'))
        return ob

    }
    /**
     * 用于拦截Controller层处理完请求，返回内容
     * @param result
     */
    @AfterReturning(returning = "result", pointcut = "serviceAspect()")
    void doAfterReturning(Object result) {
        ObjectMapper mapper = new ObjectMapper()
        try {
            logger.info("Returning >>> response={}", result.toString())
            webLogThreadLocal.get().setRespParams(mapper.writeValueAsString(result))
        } catch (JsonProcessingException e) {
            logger.info(CommonUtil.getExceptionDetail(e))
        }
        webLogThreadLocal.get().setSpendTime((System.currentTimeMillis() - startTime.get()))
        try {
            logger.info("Returning >>> " + mapper.writeValueAsString(webLogThreadLocal.get()))
        } catch (JsonProcessingException e) {
            logger.info(CommonUtil.getExceptionDetail(e))
        }
    }
    /**
     * 异常通知 用于拦截Controller层异常
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
    void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes()
        HttpServletRequest request = attributes.getRequest()
        String className = joinPoint.getTarget().getClass().getName()
        String methodName = joinPoint.getSignature().getName()
        logger.error('>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>开始异常捕捉<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n')
        logger.error('异常时间 : ' + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())))
        logger.error('请求方法 : ' + request.getMethod())
        logger.error('类方法 : ' + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName())
        logger.error('请求URL: ' + request.getRequestURL().toString())
        logger.error('获得IP: ' + CommonUtil.getRealIP(request))
        logger.error('参数: ' + CommonUtil.getArgs(joinPoint))
        logger.error('请求参数: ' + (request.getParameterMap() == null ? 'Unknow Parameter' : JSONObject.toJSON(request.getParameterMap())))
        logger.error('用户名: ' + (ShiroUtil.getUser()?.getUsername() ?: '未登录'))
        logger.error("异常:" + e)
        logger.error("异常所在类：" + className)
        logger.error("异常所在方法：" + methodName)
        logger.error('访问类型:' + (CommonUtil.isJsonRequest(request) ? 'Json' : 'Page'))
        logger.error('>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>异常捕捉完成<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n')
    }
}
