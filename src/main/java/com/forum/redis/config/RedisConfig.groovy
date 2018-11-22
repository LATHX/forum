package com.forum.redis.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.redis.connection.RedisClusterConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisNode
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import redis.clients.jedis.JedisPoolConfig

//@Configuration
@PropertySource("classpath:config/redis.properties")
class RedisConfig {
    @Value("redis.pool.max-idle")
    private Integer maxIdle
    @Value("redis.pool.max-wait")
    private Integer maxWaitMillis
    @Value("redis.pool.min-idle")
    private Integer minIdle
    @Value("redis.timeBetweenEvictionRunsMillis")
    private long timeBetweenEvictionRunsMillis
    @Value("redis.pool.max-active")
    private Integer maxTotal
    @Value("redis.cluster.nodes")
    private String clusterNodes
    @Value("redis.cluster.max-redirects")
    private Integer mmaxRedirectsac
    @Value("redis.password")
    private String password

    /**
     * JedisPoolConfig 连接池
     * @return
     */
    @Bean
    JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig()
        // 最大空闲数
        jedisPoolConfig.setMaxIdle(maxIdle)
        // 最小空闲数
        jedisPoolConfig.setMinIdle(minIdle)
        // 连接池的最大数据库连接数
        jedisPoolConfig.setMaxTotal(maxTotal)
        // 最大建立连接等待时间
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis)
        // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis)
        return jedisPoolConfig
    }

    /**
     * Redis集群的配置
     * @return
     */
    @Bean
    RedisClusterConfiguration redisClusterConfiguration(){
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration()
        String[] serverArray = clusterNodes.split(",")
        Set<RedisNode> nodes = new HashSet<RedisNode>()
        for(String ipPort:serverArray){
            String[] ipAndPort = ipPort.split(":")
            nodes.add(new RedisNode(ipAndPort[0].trim(),Integer.valueOf(ipAndPort[1])))
        }
        redisClusterConfiguration.setClusterNodes(nodes)
        redisClusterConfiguration.setMaxRedirects(mmaxRedirectsac)
        redisClusterConfiguration.setPassword(password)
        return redisClusterConfiguration
    }

    /**
     * 实例化 RedisTemplate 对象
     * @param jedisPoolConfig
     * @param redisClusterConfiguration
     * @return
     */
    @Bean
    JedisConnectionFactory JedisConnectionFactory(JedisPoolConfig jedisPoolConfig,RedisClusterConfiguration redisClusterConfiguration){
        JedisConnectionFactory JedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration,jedisPoolConfig)
        return JedisConnectionFactory
    }
    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>()
        template.setConnectionFactory(factory)
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class)
        ObjectMapper om = new ObjectMapper()
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
        jackson2JsonRedisSerializer.setObjectMapper(om)
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer()
        template.setKeySerializer(stringRedisSerializer)
        template.setHashKeySerializer(stringRedisSerializer)
        template.setValueSerializer(jackson2JsonRedisSerializer)
        template.setHashValueSerializer(jackson2JsonRedisSerializer)
        template.setEnableTransactionSupport(true)
        template.afterPropertiesSet()
        return template
    }

}
