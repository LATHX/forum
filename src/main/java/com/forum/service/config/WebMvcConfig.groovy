package com.forum.service.config

import com.alibaba.fastjson.serializer.SerializerFeature
import com.alibaba.fastjson.support.config.FastJsonConfig
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter
import com.forum.service.aspect.RequestLimitAspect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

/**
 * @Description 处理Json中文乱码问题
 * @Version 1.0
 */
@Configuration
class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    RequestLimitAspect requestLimitAspect

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter()
        FastJsonConfig fastJsonConfig = new FastJsonConfig()
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat)
        List<MediaType> fastMediaTypes = new ArrayList<>()
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8)
        fastConverter.setSupportedMediaTypes(fastMediaTypes)
        fastConverter.setFastJsonConfig(fastJsonConfig)
        converters.add(fastConverter)
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLimitAspect);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/index")
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE)
        super.addViewControllers(registry)
    }
}
