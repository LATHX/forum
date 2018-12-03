package com.forum.utils

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.redis.util.RedisUtil
import com.forum.service.config.GenerateToken
import org.aspectj.lang.JoinPoint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.servlet.http.HttpServletRequest

@Component
class CommonUtil {
    @Autowired
    RedisUtil redisUtil
    @Autowired
    GenerateToken generateToken
    synchronized String generateUUID(){
        return UUID.randomUUID()?.toString()?.replaceAll('-','')
    }
    boolean notEmpty(Object data) {
        if (data == null)
            return false;

        if (data instanceof String) {
            return data.trim().length() > 0
        } else {
            return (data != null && data.toString().trim().length()>0)
        }
    }

    boolean isNotEmpty(Object data) {
        return notEmpty(data)
    }

    boolean isEmpty(Object data) {
        return (! notEmpty(data))
    }
    /**
     * @Description 获取IP，支持使用反向代理时获得真实IP
     * !!!Nginx需要在server设置
     * proxy_set_header Host $http_host;
     * proxy_set_header X-Real-IP $remote_addr;
     * proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
     * proxy_set_header X-Forwarded-Proto $scheme;
     *
     * 在X-Forwarded-For下
     *多次反向代理后会有多个IP值，第一个ip才是真实IP
     * @param request
     * @return
     */

    String getRealIP(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        if (!isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
             } else {
                return ip;
               }
            }
           ip = request.getHeader("X-Real-IP");
        if (!isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
         return ip;
            }
        return request.getRemoteAddr();
    }

    static String getArgs(JoinPoint joinPoint){
        String s = 'Arg -> '
        joinPoint?.eachWithIndex { current_join,index ->
            s += '('+index+')'+current_join
        }
        return  s
    }
    static String getExceptionDetail(Exception e) {
        try {
            StringWriter sw = new StringWriter()
            PrintWriter pw = new PrintWriter(sw)
            e.printStackTrace(pw)
            sw.close();
            pw.close();
            return sw.toString()
        } catch (Exception e2) {
            return "ErrorInfoFromException";
        }
    }

}
