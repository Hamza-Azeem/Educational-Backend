package com.education.educational_platform.mapper;

import com.education.educational_platform.dto.TeacherDto;
import com.education.educational_platform.entity.User;

public class TeacherMapper {

    public static TeacherDto toTeacherDto(User teacher) {
        return TeacherDto.builder()
                .id(teacher.getId())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .phone(teacher.getPhone())
                .email(teacher.getEmail())
                .build();
    }
    public static User toTeacher(TeacherDto teacherDto) {
        return User.builder()
                .id(teacherDto.id())
                .phone(teacherDto.phone())
                .email(teacherDto.email())
                .firstName(teacherDto.firstName())
                .lastName(teacherDto.lastName())
                .build();
    }
}
