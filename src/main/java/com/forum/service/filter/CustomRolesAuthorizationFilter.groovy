package com.forum.service.filter

import com.alibaba.fastjson.JSONObject
import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.model.dto.CommonInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.utils.CommonUtil
import org.apache.shiro.subject.Subject
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod

import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomRolesAuthorizationFilter extends RolesAuthorizationFilter {
    @Autowired
    MessageCodeInfo messageCodeInfo
    @Autowired
    CommonInfo commonInfo

    @Override
     boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) {
        Subject subject = getSubject(req, resp)
        String[] rolesArray = (String[]) mappedValue
        //如果没有角色限制，直接放行
        if (rolesArray == null || rolesArray.length == 0) {
            return true
        }
        for (int i = 0; i < rolesArray.length; i++) {
            //若当前用户是rolesArray中的任何一个，则有权限访问
            if (subject.hasRole(rolesArray[i])) {
                return true
            }
        }
        return false
    }


    @Override
     boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest servletRequest = (HttpServletRequest) request
        HttpServletResponse servletResponse = (HttpServletResponse) response
        //处理跨域问题，跨域的请求首先会发一个options类型的请求
        if (servletRequest.getMethod().equals(HttpMethod.OPTIONS.name())) {
            return true
        }
        boolean isAccess = isAccessAllowed(request, response, mappedValue)
        if (isAccess) {
            return true
        }
        servletResponse.setCharacterEncoding("UTF-8")
        Subject subject = getSubject(request, response)
        servletResponse.setContentType("application/json;charset=UTF-8")
        servletResponse.setHeader("Access-Control-Allow-Origin", servletRequest.getHeader("Origin"))
        servletResponse.setHeader("Access-Control-Allow-Credentials", "true")
        servletResponse.setHeader("Vary", "Origin")
        String respStr
        if (CommonUtil.isJsonRequest(request)) {
            PrintWriter printWriter = servletResponse.getWriter()
            if (subject.getPrincipal() == null) {
                messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
                messageCodeInfo.setMsgInfo(Constant.LOGIN_OUT_MSG)
                commonInfo.setMsg(messageCodeInfo)
                respStr = JSONObject.toJSONString(commonInfo)
            } else {
                messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
                messageCodeInfo.setMsgInfo(Constant.LOGIN_PERMISSION_MSG)
                commonInfo.setMsg(messageCodeInfo)
                respStr = JSONObject.toJSONString(commonInfo)
            }
            printWriter.write(respStr)
            printWriter.flush()
            servletResponse.setHeader("content-Length", respStr.getBytes().length + "")
        } else {
            HttpServletResponse resp = (HttpServletResponse) response
            resp.sendRedirect(Constant.LOGIN_PAGE)
        }

        return false
    }
}
