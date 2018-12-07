package com.forum.model.entity

import com.forum.model.entity.UserEntity

/**
 * @describe: 日志打印实体
 * @author: LJL
 * @date: 2018/10/29 17:40
 * @version: 1.0
 */
class LogEntity implements Serializable {
    private long id;
    /**
     * 请求地址
     */
    private String url;
    /**
     * 请求IP
     */
    private String ip;
    /**
     * 请求方式
     */
    private String httpMethod;
    /**
     * 请求类，方法
     */
    private String classMethod;
    /**
     * 方法参数
     */
    private String args;
    /**
     * 请求参数
     */
    private String reqParams;
    /**
     * 响应参数
     */
    private String respParams;
    /**
     * 响应时间
     */
    private long spendTime;
    /**
     * 日志类型（web、service）
     */
    private String logType;
    /**
     * 用户信息
     */
    private UserEntity user;

    long getId() {
        return id
    }

    void setId(long id) {
        this.id = id
    }

    String getUrl() {
        return url
    }

    void setUrl(String url) {
        this.url = url
    }

    String getIp() {
        return ip
    }

    void setIp(String ip) {
        this.ip = ip
    }

    String getHttpMethod() {
        return httpMethod
    }

    void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod
    }

    String getClassMethod() {
        return classMethod
    }

    void setClassMethod(String classMethod) {
        this.classMethod = classMethod
    }

    String getArgs() {
        return args
    }

    void setArgs(String args) {
        this.args = args
    }

    String getReqParams() {
        return reqParams
    }

    void setReqParams(String reqParams) {
        this.reqParams = reqParams
    }

    String getRespParams() {
        return respParams
    }

    void setRespParams(String respParams) {
        this.respParams = respParams
    }

    long getSpendTime() {
        return spendTime
    }

    void setSpendTime(long spendTime) {
        this.spendTime = spendTime
    }

    String getLogType() {
        return logType
    }

    void setLogType(String logType) {
        this.logType = logType
    }

    UserEntity getUser() {
        return user
    }

    void setUser(UserEntity user) {
        this.user = user
    }
}
