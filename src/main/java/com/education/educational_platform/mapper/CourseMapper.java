package com.education.educational_platform.mapper;

import com.education.educational_platform.dto.CourseDto;
import com.education.educational_platform.entity.Course;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static com.education.educational_platform.mapper.TeacherMapper.toTeacher;
import static com.education.educational_platform.mapper.TeacherMapper.toTeacherDto;

public class CourseMapper {
    public static CourseDto toCourseDto(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .price(course.getPrice() != null ? course.getPrice().doubleValue() : 99999)
                .studentNumber(course.getStudentsNumber())
                .teacherDto(course.getTeacher() != null ? toTeacherDto(course.getTeacher()) : null)
                .build();
    }
    public static Course toCourse(CourseDto courseDto) {
        return Course.builder()
                .id(courseDto.id())
                .title(courseDto.title())
                .description(courseDto.description())
                .price(BigDecimal.valueOf(courseDto.price()).setScale(2, RoundingMode.HALF_UP))
                .studentsNumber(courseDto.studentNumber())
                .teacher(courseDto.teacherDto() != null ? toTeacher(courseDto.teacherDto()) : null)
                .build();
    }
}
