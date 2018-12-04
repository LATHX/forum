package com.forum.service.aspect

import com.alibaba.fastjson.JSONObject
import com.forum.model.dto.MessageCodeInfo

import com.forum.redis.util.RedisUtil
import com.forum.service.exception.RequestLimitException
import com.forum.utils.CommonUtil
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.PropertySource
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse

import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component
import com.github.pagehelper.util.StringUtil;

/**
 * @author LJL
 * @date 2018年11月2日 下午2:22:43
 * 类说明:限流
 */
@Aspect
@Component
@PropertySource('classpath:config/configuration.properties')
@ConfigurationProperties(prefix = "http")
class RequestLimitAspect extends HandlerInterceptorAdapter{
    private static final Logger logger = LoggerFactory.getLogger(RequestLimitAspect.class)
    @Autowired
    private RedisUtil RedisUtil
    @Autowired
    private MessageCodeInfo msg

    private int LIMIT_TIMEOUT
    private int LIMIT_COUNT
    private String LIMIT_PATH
    String getLIMIT_PATH() {
        return LIMIT_PATH
    }

    void setLIMIT_PATH(String LIMIT_PATH) {
        this.LIMIT_PATH = LIMIT_PATH
    }

    int getLIMIT_TIMEOUT() {
        return LIMIT_TIMEOUT
    }

    void setLIMIT_TIMEOUT(int LIMIT_TIMEOUT) {
        this.LIMIT_TIMEOUT = LIMIT_TIMEOUT
    }

    int getLIMIT_COUNT() {
        return LIMIT_COUNT
    }

    void setLIMIT_COUNT(int LIMIT_COUNT) {
        this.LIMIT_COUNT = LIMIT_COUNT
    }
    /**
     *
     * @param request
     * @param response
     * @param handler
     * @return boolean
     * @description 创建一个定时器,这个定时器设定在time规定的时间之后会执行上TimeTask的remove方法，也就是说在这个时间后它可以重新访问
     * @throws RequestLimitException
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws RequestLimitException {
        try {

            if (request == null) {
                throw new RequestLimitException("Lost HttpServletRequest Param");
            }
            String ip = CommonUtil.getRealIP(request);
            String url = request.getRequestURL().toString();
            String key = "req_limit_".concat(url).concat(ip);
            if(url.substring(url.lastIndexOf('/')+1) == 'limit'){
                return true
            }
            if (!RedisUtil.hasKey(key) || StringUtil.isEmpty(RedisUtil.get(key))) {
                RedisUtil.set(key,String.valueOf(1));
            } else {
                Integer getValue = Integer.parseInt(RedisUtil.get(key))+1;
                RedisUtil.set(key, String.valueOf(getValue));
                RedisUtil.expire(key, LIMIT_TIMEOUT?.toLong())
            }
            int count =Integer.parseInt(RedisUtil.get(key));
            if (count > 0) {
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        redisDel(key);
                    }
                };
                timer.schedule(timerTask, LIMIT_TIMEOUT);
            }
            if (count > LIMIT_COUNT) {
                logger.info("User IP[" + ip + "]URL[" + url + "]more than times[" + LIMIT_COUNT + "]");
               render(request, response);
                return false;
            }
        }catch (RequestLimitException e){
            throw e;
        }catch (Exception e){
            logger.error("RequestLimit Error: ",e);
        }
        return true
    }

/**
 * 返回客户端的错误信息
 * @param response
 * @param msg
 * @throws Exception
 */
    void render(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        response.setContentType("application/json;charset=UTF-8");
//        response.sendRedirect(LIMIT_PATH)
        request.getRequestDispatcher(LIMIT_PATH).forward(request, response)
    }
     void redisDel(String key){
        RedisUtil.del(key)
    }
}
