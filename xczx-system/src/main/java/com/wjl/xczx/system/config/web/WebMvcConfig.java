package com.wjl.xczx.system.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @description
 * @date 2023/4/14
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    //@Override
    //public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    //    ObjectMapper objectMapper=new ObjectMapper();
    //
    //    JavaTimeModule javaTimeModule=new JavaTimeModule();
    //    javaTimeModule.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    //    javaTimeModule.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    //    //localDateTime按照 "yyyy-MM-dd HH:mm:ss"的格式进行序列化、反序列化
    //
    //    objectMapper.registerModule(javaTimeModule);
    //
    //    ArrayList<HttpMessageConverter<?>> converterArrayList = new ArrayList<>();
    //    converterArrayList.add(new MappingJackson2HttpMessageConverter(objectMapper));
    //    converters.addAll(0, converterArrayList);
    //}


}
