package com.education.educational_platform.mapper;

import com.education.educational_platform.dto.CourseDto;
import com.education.educational_platform.entity.Course;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


public class CourseMapperTest {
    @Test
    void toCourseWillReturnCourse() {
        // Arrange
        CourseDto courseDto = CourseDto.builder()
                .id(1L)
                .title("title")
                .price(1234.1234)
                .description("description")
                .build();
        // Act
        Course actual = CourseMapper.toCourse(courseDto);
        // Assert
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getDescription()).isEqualTo("description");
        assertThat(actual.getTitle()).isEqualTo("title");
        assertThat(actual.getPrice()).isEqualTo(BigDecimal.valueOf(1234.12));


    }
}
