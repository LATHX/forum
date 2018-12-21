package com.forum.service.aspect

import com.forum.global.Constant
import com.forum.redis.util.RedisUtil
import com.forum.service.exception.RequestLimitException
import com.forum.utils.CommonUtil
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author LJL
 * @date 2018年11月2日 下午2:22:43
 * 类说明:限流
 */
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class RequestLimitAspect extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(RequestLimitAspect.class)
    private int LIMIT_TIMEOUT = Constant.LIMIT_TIMEOUT?.toInteger()
    private int LIMIT_COUNT = Constant.LIMIT_COUNT?.toInteger()
    private String LIMIT_PATH = Constant.LIMIT_PATH
    private String param = ''

    /**
     *
     * @param request
     * @param response
     * @param handler
     * @return boolean
     * @description 创建一个定时器 , 这个定时器设定在time规定的时间之后会执行上TimeTask的remove方法，也就是说在这个时间后它可以重新访问
     * @throws RequestLimitException
     */
    @Override
    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws RequestLimitException {
        try {

            String url = request.getRequestURL().toString()
            String ip = CommonUtil.getRealIP(request)
            if (request == null) {
                throw new RequestLimitException("Lost HttpServletRequest Param")
            }
            if (url.substring(url.lastIndexOf('/')) == LIMIT_PATH || url.substring(url.lastIndexOf('/')) == '/cookie' || url.substring(url.lastIndexOf('/')) == '/setcookie.html') {
                return true
            }
            String uuidCookie = CommonUtil.getCookies(request, 'custom.name')
            if (CommonUtil.isEmpty(uuidCookie)) {
                    renderCookie(request, response)
            }
            String key = "req_limit_".concat(url).concat(ip).concat(uuidCookie)

            if (!RedisUtil.hasKey(key) || CommonUtil.isEmpty(RedisUtil.get(key))) {
                RedisUtil.set(key, String.valueOf(1))
            } else {
                Integer getValue = RedisUtil.get(key)?.toString()?.toInteger() + 1
                RedisUtil.set(key, String.valueOf(getValue))
                RedisUtil.expire(key, (LIMIT_TIMEOUT / 1000)?.toLong())
            }
            int count = RedisUtil.get(key)?.toString()?.toInteger()
            if (count > 0) {
                Timer timer = new Timer()
                TimerTask timerTask = new TimerTask() {
                    @Override
                    void run() {
                        redisDel(key)
                    }
                }
                timer.schedule(timerTask, LIMIT_TIMEOUT)
            }
            if (count > LIMIT_COUNT) {
                logger.info("User IP[" + ip + "]URL[" + url + "]more than times[" + LIMIT_COUNT + "]")
                render(request, response)
                return false
            }
        } catch (RequestLimitException e) {
            throw e
        } catch (Exception e) {
            logger.error("RequestLimit Error: ", e)
        }
        return true
    }

/**
 * 返回客户端的错误信息
 * @param response
 * @param msg
 * @throws Exception
 */
    void render(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        response.setContentType("application/json;charset=UTF-8")
//        response.sendRedirect(LIMIT_PATH)
        request.setAttribute('msg', param)
        request.getRequestDispatcher(LIMIT_PATH).forward(request, response)

    }

    void renderCookie(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getRequestDispatcher('/cookie').forward(request, response)
    }

    void redisDel(String key) {
        RedisUtil.del(key)
    }
}
