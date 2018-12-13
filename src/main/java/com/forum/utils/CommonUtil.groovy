package com.forum.utils

import com.forum.redis.util.RedisUtil
import com.forum.service.config.GenerateToken
import org.aspectj.lang.JoinPoint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.servlet.http.HttpServletRequest
import java.text.SimpleDateFormat

@Component
class CommonUtil {
    @Autowired
    GenerateToken generateToken

    static String generateUUID() {
        return UUID.randomUUID()?.toString()?.replaceAll('-', '')
    }

    static boolean notEmpty(Object data) {
        if (data == null)
            return false;

        if (data instanceof String) {
            return data.trim().length() > 0
        } else {
            return (data != null && data.toString().trim().length() > 0)
        }
    }

    static boolean isNotEmpty(Object data) {
        return notEmpty(data)
    }

    static boolean isEmpty(Object data) {
        return (!notEmpty(data))
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
     * 多次反向代理后会有多个IP值，第一个ip才是真实IP
     * @param request
     * @return
     */

    static String getRealIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For")
        if (!isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",")
            if (index != -1) {
                return ip.substring(0, index)
            } else {
                return ip
            }
        }
        ip = request.getHeader("X-Real-IP")
        if (!isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip
        }
        return request.getRemoteAddr()
    }

    static String getArgs(JoinPoint joinPoint) {
        String s = 'Arg -> '
        joinPoint?.eachWithIndex { current_join, index ->
            s += '(' + index + ')' + current_join
        }
        return s
    }

    static String getExceptionDetail(Exception e) {
        try {
            StringWriter sw = new StringWriter()
            PrintWriter pw = new PrintWriter(sw)
            e.printStackTrace(pw)
            sw.close()
            pw.close()
            return sw.toString()
        } catch (Exception e2) {
            return "ErrorInfoFromException"
        }
    }

    static boolean isJsonRequest(HttpServletRequest request) {
        String accept = request.getHeader('accept')
        if (accept == null) return false
        if (accept.indexOf('json') != -1 || accept.indexOf('text/javascript') != -1) {
            return true
        }
        return false
    }

    static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
    }

    static byte[] getBytesFromObject(Serializable obj) {
        if (obj == null) {
            return null
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream()
        ObjectOutputStream oo = new ObjectOutputStream(bo)
        oo.writeObject(obj)
        return bo.toByteArray()
    }


    static Object getObjectFromBytes(byte[] objBytes) {
        if (objBytes == null || objBytes.length == 0) {
            return null
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes)
        ObjectInputStream oi = new ObjectInputStream(bi)
        return oi.readObject()
    }

    static boolean hasRedisKey(String key) {
        boolean hasKey = RedisUtil.hasKey(key)
        if (hasKey && RedisUtil.getExpire(key) == 0) {
            RedisUtil.del(key)
            return false
        } else if (hasKey) {
            return true
        }
        return false
    }

    static boolean setRedisKeyAndTime(String key, String value, long time) {
        boolean keyFlag = RedisUtil.set(key, value)
        boolean expireFlag = RedisUtil.expire(key, time)
        if (keyFlag == false || expireFlag == false) {
            return false
        }
        return true
    }

    static String randomCode(int size) {
        Random rd = new Random()
        StringBuilder sb = new StringBuilder()
        char[] c = allCharacter()
        for (int index = 0; index < size; index++) {
            sb.append(c[rd.nextInt(c.length)])
        }
        return sb.toString()
    }


    static char[] allCharacter() {
        "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM".toCharArray()
    }
    static String getURLWithoutContext(HttpServletRequest request){
        StringBuffer url = request.getRequestURL()
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString()
        return tempContextUrl

    }
    static String replaceIllegalCharacter(String s){
        return s.replaceAll("[^qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM1234567890\"!#\$%&\'()*+,-./:\\\\;<=>?@^_{|}\\[\\]~\t]","")
    }
}
