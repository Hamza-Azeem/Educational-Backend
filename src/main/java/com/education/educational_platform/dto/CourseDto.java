package com.education.educational_platform.dto;

import lombok.Builder;

@Builder
public record CourseDto(
        Long id,
        String title,
        String description,
        Double price,
        Integer studentNumber,
        TeacherDto teacherDto
) {
}
