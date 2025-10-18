package com.xinnsuu.seatflow.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.xinnsuu.seatflow.converter.StringToClassroomLayoutConverter;
import com.xinnsuu.seatflow.converter.StringToStudentConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private StringToStudentConverter stringToStudentConverter;

    @Autowired
    private StringToClassroomLayoutConverter stringToClassroomLayoutConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToStudentConverter);
        registry.addConverter(stringToClassroomLayoutConverter);
    }
}
