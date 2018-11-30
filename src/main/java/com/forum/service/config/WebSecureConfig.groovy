package com.forum.service.config

import com.forum.global.Constant
import com.forum.service.filter.CustomRolesAuthorizationFilter
import com.forum.service.security.realm.PasswordRealm
import com.forum.service.service.ShiroService
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.filter.DelegatingFilterProxy;
import redis.clients.jedis.JedisPool;

import javax.servlet.Filter;
@Configuration
class WebSecureConfig {
    private String CACHE_KEY = 'shiro:cache:'
    private String SESSION_KEY = 'shiro:session:*'
    private String NAME = 'custom.name'
    private String VALUE = '/'
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, ShiroService shiroService) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = new LinkedHashMap<>(1);
        filterMap.put("roles", rolesAuthorizationFilter());
        shiroFilter.setFilters(filterMap);
        shiroFilter.setFilterChainDefinitionMap(shiroService.loadFilterChainDefinitions());
        shiroFilter.setLoginUrl('/loginpage')
        return shiroFilter;
    }

    @Bean
    public CustomRolesAuthorizationFilter rolesAuthorizationFilter() {
        return new CustomRolesAuthorizationFilter();
    }

    @Bean("securityManager")
    public SecurityManager securityManager(Realm realm, SessionManager sessionManager, RedisCacheManager redisCacheManager) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRememberMeManager(rememberMeManager());
        manager.setSessionManager(sessionManager);
        manager.setCacheManager(redisCacheManager);
        manager.setRealm(realm);
        return manager;
    }

    @Bean("defaultAdvisorAutoProxyCreator")
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        //指定强制使用cglib为action创建代理对象
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean("delegatingFilterProxy")
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }/*
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redis_host+':'+redis_port);
        redisManager.setPassword(redis_password);
        return redisManager;
    }*/
    @Bean
    public RedisManager redisManager(JedisPool jedisPool) {
        RedisManager redisManager = new RedisManager();
        redisManager.setJedisPool(jedisPool);
        return redisManager;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        redisCacheManager.setExpire(86400);
        redisCacheManager.setKeyPrefix(CACHE_KEY);
        return redisCacheManager;
    }

    @Bean
    public RedisSessionDAO redisSessionDAO(RedisManager redisManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setExpire(86400);
        redisSessionDAO.setKeyPrefix(SESSION_KEY);
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }

    @Bean
    public DefaultWebSessionManager sessionManager(RedisSessionDAO sessionDAO, SimpleCookie simpleCookie) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(simpleCookie);
        return sessionManager;
    }

    @Bean
    public SimpleCookie simpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName(NAME);
        simpleCookie.setValue(VALUE);
        return simpleCookie;
    }

    @Bean
    public Realm realm(RedisCacheManager redisCacheManager) {
        PasswordRealm realm = new PasswordRealm();
        realm.setCacheManager(redisCacheManager);
        realm.setAuthenticationCachingEnabled(false);
        realm.setAuthorizationCachingEnabled(false);
        return realm;
    }
    /**
     * cookie对象;会话Cookie模板 ,默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid或rememberMe，自定义
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：

        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //<!-- 记住我cookie生效时间7天 ,单位秒;-->
        simpleCookie.setMaxAge(60*60*24*7);
        return simpleCookie;
    }
    /**
     * cookie管理对象;记住我功能,rememberMe管理器
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64?.getDecoder().decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }
    /**
     * FormAuthenticationFilter 过滤器 过滤记住我
     * @return
     */
    @Bean
    public FormAuthenticationFilter formAuthenticationFilter(){
        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
        //对应前端的checkbox的name = rememberMe
        formAuthenticationFilter.setRememberMeParam("rememberMe");
        return formAuthenticationFilter;
    }

    String getRedis_host() {
        return redis_host
    }

    void setRedis_host(String redis_host) {
        this.redis_host = redis_host
    }

    String getRedis_password() {
        return redis_password
    }

    void setRedis_password(String redis_password) {
        this.redis_password = redis_password
    }

    String getRedis_port() {
        return redis_port
    }

    void setRedis_port(String redis_port) {
        this.redis_port = redis_port
    }

    String getCACHE_KEY() {
        return CACHE_KEY
    }

    void setCACHE_KEY(String CACHE_KEY) {
        this.CACHE_KEY = CACHE_KEY
    }

    String getSESSION_KEY() {
        return SESSION_KEY
    }

    void setSESSION_KEY(String SESSION_KEY) {
        this.SESSION_KEY = SESSION_KEY
    }

    String getNAME() {
        return NAME
    }

    void setNAME(String NAME) {
        this.NAME = NAME
    }

    String getVALUE() {
        return VALUE
    }

    void setVALUE(String VALUE) {
        this.VALUE = VALUE
    }
}
