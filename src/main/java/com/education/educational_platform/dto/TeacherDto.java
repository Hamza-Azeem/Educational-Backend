package com.education.educational_platform.dto;

import lombok.Builder;

@Builder
public record TeacherDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone
) {
}
