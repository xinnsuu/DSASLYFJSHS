package com.xinnsuu.seatflow.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.xinnsuu.seatflow.model.Student;
import com.xinnsuu.seatflow.repository.StudentRepository;

@Component
public class StringToStudentConverter implements Converter<String, Student> {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return studentRepository.findById(source).orElse(null);
    }
}
