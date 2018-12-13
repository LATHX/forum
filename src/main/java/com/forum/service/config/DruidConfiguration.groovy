package com.forum.service.config

import com.alibaba.druid.pool.DruidDataSource
import com.alibaba.druid.support.http.StatViewServlet
import com.alibaba.druid.support.http.WebStatFilter
import com.forum.utils.CommonUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

import javax.sql.DataSource
import java.sql.SQLException

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DruidConfiguration {
    private String url
    private String username
    private String password
    private String driverClassName
    private int initialSize
    private int minIdle
    private int maxActive
    private int maxWait
    private int timeBetweenEvictionRunsMillis
    private int minEvictableIdleTimeMillis
    private String validationQuery
    private boolean testWhileIdle
    private boolean testOnBorrow
    private boolean testOnReturn
    private boolean poolPreparedStatements
    private int maxPoolPreparedStatementPerConnectionSize
    private String filters
    private String connectionProperties
    private String webUsername
    private String webPassword
    private String allowIp
    private String denyIp
    private static final Logger logger = LoggerFactory.getLogger(DruidConfiguration.class)

    @Bean
    public ServletRegistrationBean druidServlet() {
        logger.info("init Druid Servlet Configuration ")
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*")
        // IP白名单
        if (CommonUtil.isNotEmpty(allowIp))
            servletRegistrationBean.addInitParameter("allow", allowIp)
        // IP黑名单(共同存在时，deny优先于allow)
        if (CommonUtil.isNotEmpty(denyIp))
            servletRegistrationBean.addInitParameter("allow", denyIp)
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", webUsername)
        servletRegistrationBean.addInitParameter("loginPassword", webPassword)
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "false")
        return servletRegistrationBean
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter())
        filterRegistrationBean.addUrlPatterns("/*")
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*")
        return filterRegistrationBean
    }


    @Bean
    //声明其为Bean实例
    @Primary
    //在同样的DataSource中，首先使用被标注的DataSource
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource()
        datasource.setUrl(url)
        datasource.setUsername(username)
        datasource.setPassword(password)
        datasource.setDriverClassName(driverClassName)

        //configuration
        datasource.setInitialSize(initialSize)
        datasource.setMinIdle(minIdle)
        datasource.setMaxActive(maxActive)
        datasource.setMaxWait(maxWait)
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis)
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis)
        datasource.setValidationQuery(validationQuery)
        datasource.setTestWhileIdle(testWhileIdle)
        datasource.setTestOnBorrow(testOnBorrow)
        datasource.setTestOnReturn(testOnReturn)
        datasource.setPoolPreparedStatements(poolPreparedStatements)
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize)
        try {
            datasource.setFilters(filters)
        } catch (SQLException e) {
            System.err.println("druid configuration initialization filter: " + e)
        }
        datasource.setConnectionProperties(connectionProperties)
        return datasource
    }

    String getAllowIp() {
        return allowIp
    }

    void setAllowIp(String allowIp) {
        this.allowIp = allowIp
    }

    String getDenyIp() {
        return denyIp
    }

    void setDenyIp(String denyIp) {
        this.denyIp = denyIp
    }

    String getWebUsername() {
        return webUsername
    }

    void setWebUsername(String webUsername) {
        this.webUsername = webUsername
    }

    String getWebPassword() {
        return webPassword
    }

    void setWebPassword(String webPassword) {
        this.webPassword = webPassword
    }

    String getUrl() {
        return url
    }

    void setUrl(String url) {
        this.url = url
    }

    String getUsername() {
        return username
    }

    void setUsername(String username) {
        this.username = username
    }

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }

    String getDriverClassName() {
        return driverClassName
    }

    void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName
    }

    int getInitialSize() {
        return initialSize
    }

    void setInitialSize(int initialSize) {
        this.initialSize = initialSize
    }

    int getMinIdle() {
        return minIdle
    }

    void setMinIdle(int minIdle) {
        this.minIdle = minIdle
    }

    int getMaxActive() {
        return maxActive
    }

    void setMaxActive(int maxActive) {
        this.maxActive = maxActive
    }

    int getMaxWait() {
        return maxWait
    }

    void setMaxWait(int maxWait) {
        this.maxWait = maxWait
    }

    int getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis
    }

    void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis
    }

    int getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis
    }

    void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis
    }

    String getValidationQuery() {
        return validationQuery
    }

    void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery
    }

    boolean getTestWhileIdle() {
        return testWhileIdle
    }

    void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle
    }

    boolean getTestOnBorrow() {
        return testOnBorrow
    }

    void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow
    }

    boolean getTestOnReturn() {
        return testOnReturn
    }

    void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn
    }

    boolean getPoolPreparedStatements() {
        return poolPreparedStatements
    }

    void setPoolPreparedStatements(boolean poolPreparedStatements) {
        this.poolPreparedStatements = poolPreparedStatements
    }

    int getMaxPoolPreparedStatementPerConnectionSize() {
        return maxPoolPreparedStatementPerConnectionSize
    }

    void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
        this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize
    }

    String getFilters() {
        return filters
    }

    void setFilters(String filters) {
        this.filters = filters
    }

    String getConnectionProperties() {
        return connectionProperties
    }

    void setConnectionProperties(String connectionProperties) {
        this.connectionProperties = connectionProperties
    }
}
