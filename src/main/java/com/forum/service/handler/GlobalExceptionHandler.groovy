package com.forum.service.handler

import com.alibaba.fastjson.JSONObject
import com.forum.model.entity.LogEntity
import com.forum.utils.CommonUtil
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException

import javax.servlet.http.HttpServletRequest;

/**
 * @describe: 全局异常处理
 * @author: LJL
 * @date: 2018/10/29 17:40
 * @version: 1.0
 */
@ControllerAdvice(annotations = [RestController.class])
class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 默认未知异常
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        printLog(request,e,'Default Error');
        return 'Default Error'
    }
    /**
     * 参数异常
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = [HttpMessageNotReadableException.class, MissingServletRequestPartException.class ,MissingServletRequestParameterException.class, MultipartException.class])
    @ResponseBody
    public String httpMessageNotReadableExceptionErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        printLog(request,e,'Args Error');
        return 'Args Error'
    }
    /**
     * contentType异常
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = [HttpMediaTypeNotSupportedException.class])
    @ResponseBody
    public String httpMediaTypeNotSupportedExceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        printLog(request,e,'ContentType Error');
        return 'ContentType Error'
    }

    /**
     * 异常信息打印日志
     * @param request
     * @param e
     * @param baseView
     */
    private void printLog(HttpServletRequest request,Exception e,String s){
        logger.error(CommonUtil.getExceptionDetail(e));
        LogEntity logEntity = new LogEntity();
        logEntity.setHttpMethod(request.getMethod());
        logEntity.setUrl(request.getRequestURL().toString());
        logEntity.setIp(CommonUtil.getRealIP(request));
        logEntity.setArgs(CommonUtil.getArgs(request));
        logEntity.setLogType(900);
//        logEntity.setReqParams(request.getParameterMap()?.size()==0?'':JSONObject.toJSON(request.getParameterMap()));
        logEntity.setRespParams(CommonUtil.isNotEmpty(s)?JSONObject.toJSON(s):'');
        logger.error(">>>"+JSONObject.toJSON(logEntity));
    }
}
