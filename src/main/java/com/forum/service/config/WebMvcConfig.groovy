package com.forum.service.config

import com.alibaba.fastjson.serializer.SerializerFeature
import com.alibaba.fastjson.support.config.FastJsonConfig
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter
import com.forum.service.aspect.RequestLimitAspect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

/**
 * @Description 处理Json中文乱码问题, 映射上传图片路径
 * @Version 2.0
 */
@Configuration
class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    RequestLimitAspect requestLimitAspect

    @Value('${web.upload-userimg-path}')
    private String userImgPath
    @Value('${web.upload-userbackgroundimg-path}')
    private String userBackgroundImgPath
    @Value('${web.upload-userpostimage-path}')
    private String userPostImgPath

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
    /**
     * 系统默认配置 addResourceLocations
     * 添加的配置 addResourceHandler 表示将/images/userImg/**..映射到D:目录
     * @param registry
     */
    @Override
    void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/")

        registry.addResourceHandler("/images/userImg/**").addResourceLocations("file:" + userImgPath)
        registry.addResourceHandler("/images/userBackgroundImg/**").addResourceLocations("file:" + userBackgroundImgPath)
        registry.addResourceHandler("/images/userPostImg/**").addResourceLocations("file:" + userPostImgPath)
        super.addResourceHandlers(registry);
    }
}
