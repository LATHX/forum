package com.forum.redis.config

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.context.annotation.Lazy
import org.springframework.core.PriorityOrdered
import org.springframework.data.redis.connection.RedisClusterConfiguration
import org.springframework.data.redis.connection.RedisNode
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration

import java.lang.reflect.Method
import java.time.Duration
import java.util.HashSet
import java.util.Set


import javax.annotation.Resource


import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer


import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping


@Configuration
@EnableCaching // 开启缓存支持
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig extends CachingConfigurerSupport{


    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory
    @Value('${spring.redis.lettuce.pool.max-idle}')
    private Integer maxIdle
    @Value('${spring.redis.lettuce.pool.min-idle}')
    private Integer minIdle
    @Value('${spring.redis.lettuce.pool.max-active}')
    private Integer maxActive
    @Value('${spring.redis.lettuce.pool.max-wait}')
    private Integer maxWait
    @Value('${spring.redis.password}')
    private String password
    @Value('${spring.redis.database}')
    private String database
    @Value('${spring.redis.timeout}')
    private Integer timeout
    @Value('${spring.redis.host}')
    private String host
    @Value('${spring.redis.port}')
    private String port
//    @Value('${spring.redis.cluster.nodes}')
//    private String clusterNodes
//    @Value('${spring.redis.cluster.max-redirects}')
//    private Integer maxRedirects


    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuffer sb = new StringBuffer()
                sb.append(target.getClass().getName())
                sb.append(method.getName())
                for (Object obj : params) {
                    sb.append(obj.toString())
                }
                return sb.toString()
            }
        }
    }


    // 缓存管理器
    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(lettuceConnectionFactory)
        @SuppressWarnings("serial")
        Set<String> cacheNames = new HashSet<String>() {
            {
                add("codeNameCache")
            }
        }
        builder.initialCacheNames(cacheNames)
        return builder.build()
    }


    /**
     * RedisTemplate配置
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        // 设置序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
                Object.class)
        ObjectMapper om = new ObjectMapper()
        om.setVisibility(PropertyAccessor.ALL, Visibility.ANY)
        om.enableDefaultTyping(DefaultTyping.NON_FINAL)
        jackson2JsonRedisSerializer.setObjectMapper(om)
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>()
        redisTemplate.setConnectionFactory(lettuceConnectionFactory)
        RedisSerializer<?> stringSerializer = new StringRedisSerializer()
        redisTemplate.setKeySerializer(stringSerializer)// key序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer)// value序列化
        redisTemplate.setHashKeySerializer(stringSerializer)// Hash key序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer)// Hash value序列化
        redisTemplate.afterPropertiesSet()
        return redisTemplate
    }
    @Bean
    public GenericObjectPoolConfig genericObjectPoolConfig() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig()
        genericObjectPoolConfig.setMaxIdle(maxIdle)
        genericObjectPoolConfig.setMinIdle(minIdle)
        genericObjectPoolConfig.setMaxTotal(maxActive)
        genericObjectPoolConfig.setMaxWaitMillis(maxWait)
        return genericObjectPoolConfig
    }
/*
    @Bean()
    LettuceConnectionFactory lettuceConnectionFactory(GenericObjectPoolConfig genericObjectPoolConfig) {
        // 单机版配置
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration()
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration()
        if(host != null){
            redisStandaloneConfiguration.setDatabase(database)
            redisStandaloneConfiguration.setHostName(host)
            redisStandaloneConfiguration.setPort(port)
            redisStandaloneConfiguration.setPassword(RedisPassword.of(password))
        }
//        if(clusterNodes != null){
//            // 集群版配置
//             String[] serverArray = clusterNodes.split(",")
//             Set<RedisNode> nodes = new HashSet<RedisNode>()
//             for (String ipPort : serverArray) {
//                 String[] ipAndPort = ipPort.split(":")
//                 nodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])))
//                 }
//              redisClusterConfiguration.setPassword(RedisPassword.of(password))
//              redisClusterConfiguration.setClusterNodes(nodes)
//              redisClusterConfiguration.setMaxRedirects(maxRedirects)
//        }


         LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder() .commandTimeout(Duration.ofMillis(timeout)) .poolConfig(genericObjectPoolConfig) .build()
         LettuceConnectionFactory factory = new LettuceConnectionFactory(redisStandaloneConfiguration,clientConfig)
         return factory
         }
*/

}