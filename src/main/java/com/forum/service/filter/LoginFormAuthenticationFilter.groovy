package com.forum.service.filter

import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.subject.Subject
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter

import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

class LoginFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {

    }
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        super.isAccessAllowed(request, response, mappedValue)
    }
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        println("error")
        return super.onAccessDenied(request, response);
    }
}
