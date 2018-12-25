package com.forum.utils

import com.forum.redis.util.RedisUtil
import com.forum.service.config.GenerateToken
import org.aspectj.lang.JoinPoint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.sql.Timestamp
import java.text.DateFormat
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

    static String getURLWithoutContext(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL()
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString()
        return tempContextUrl

    }

    static String replaceIllegalCharacter(String s) {
        return s.replaceAll("[^qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM1234567890\"!#\$%&\'()*+,-./:\\\\;<=>?@^_{|}\\[\\]~\t]", "")
    }

    static String getCookies(HttpServletRequest request, String name) {
        Cookie[] cookies = request?.getCookies();
        for (int i = 0; i < cookies?.length; i++) {
            if (cookies[i].getName() == name) {
                return cookies[i].getValue()
            }
        }
        return null
    }

    static void addCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60 * 1000)
        response.addCookie(cookie);
    }
    /**
     * 将java.sql.Timestamp对象转化为String字符串
     * @param time
     *            要格式的java.sql.Timestamp对象
     * @param strFormat
     *            输出的String字符串格式的限定（如："yyyy-MM-dd HH:mm:ss"）
     * @return 表示日期的字符串
     */
    static String dateToStr(Timestamp time) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = df.format(time);
        return str;
    }
    /**
     * 将String字符串转换为java.sql.Timestamp格式日期,用于数据库保存
     * @param strDate
     *            表示日期的字符串
     * @param dateFormat
     *            传入字符串的日期表示格式（如："yyyy-MM-dd HH:mm:ss"）
     * @return java.sql.Timestamp类型日期对象（如果转换失败则返回null）
     */
    static Timestamp strToSqlDate(String strDate) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null
        date = sf.parse(strDate)
        Timestamp dateSQL = new Timestamp(date.getTime());
        return dateSQL;
    }

    static Timestamp getCurrentTimestamp() {
        return new Timestamp((new Date()).getTime())
    }

    static String getDeviceInfo(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent").toLowerCase()
        if (userAgent.indexOf("micromessenger") != -1) {
            return '微信登录'
        } else if (userAgent.indexOf("android") != -1) {
            return '安卓'
        } else if (userAgent.indexOf("iphone") != -1) {
            return 'iPhone'
        } else if (userAgent.indexOf("ipad") != -1) {
            return 'iPad'
        } else if (userAgent.indexOf("ipod") != -1) {
            return 'iPod'
        } else {
            return '电脑'
        }
    }

    static boolean isNumber(String number){
        if(isEmpty(number)) return false
        return number?.isNumber()
    }

}
