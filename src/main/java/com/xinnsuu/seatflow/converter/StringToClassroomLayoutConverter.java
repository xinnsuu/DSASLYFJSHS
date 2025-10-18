package com.xinnsuu.seatflow.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.xinnsuu.seatflow.model.ClassroomLayout;
import com.xinnsuu.seatflow.repository.ClassroomLayoutRepository;

@Component
public class StringToClassroomLayoutConverter implements Converter<String, ClassroomLayout> {

    @Autowired
    private ClassroomLayoutRepository classroomLayoutRepository;

    @Override
    public ClassroomLayout convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        long layoutId = Long.parseLong(source);
        return classroomLayoutRepository.findById(layoutId).orElse(null);
    }
}
