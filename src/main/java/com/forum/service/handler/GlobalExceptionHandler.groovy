package com.forum.service.handler

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartException
import org.springframework.web.multipart.support.MissingServletRequestPartException

import javax.servlet.http.HttpServletRequest
/**
 * @describe: 全局异常处理
 * @author: LJL
 * @date: 2018/10/29 17:40
 * @version: 1.0
 */
//@ControllerAdvice(annotations = [RestController.class])
@Order(3)
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
        logger.error('Error Fix Complete')
    }
}
