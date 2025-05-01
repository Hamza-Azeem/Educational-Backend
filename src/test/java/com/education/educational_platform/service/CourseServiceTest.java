package com.education.educational_platform.service;

import com.education.educational_platform.dto.CourseDto;
import com.education.educational_platform.entity.Course;
import com.education.educational_platform.exception.InvalidRequestException;
import com.education.educational_platform.exception.ResourceNotFoundException;
import com.education.educational_platform.repository.CourseRepository;
import com.education.educational_platform.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    @InjectMocks
    private CourseServiceImpl underTest;
    @Mock
    private CourseRepository courseRepository;

    @Test
    void findAllWillReturnCoursePage() {
        // Arrange
        Course course1 = Course
                .builder()
                .id(1L)
                .title("title")
                .build();
        Course course2 = Course
                .builder()
                .id(2L)
                .title("title2")
                .build();
        List<Course> courses = Arrays.asList(course1, course2);
        int pageNumber = 0;
        int pageSize = 2;
        String sort = "title";
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sort));
        Page<Course> page = new PageImpl<>(courses, pageable, courses.size());
        when(courseRepository.findAll(pageable)).thenReturn(page);
        // Act
        List<CourseDto> actual = underTest.findAll(pageNumber, pageSize, "title");
        // Assert
        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual.get(0).title()).isEqualTo(course1.getTitle());
        assertThat(actual.get(1).title()).isEqualTo(course2.getTitle());
    }

    @Test
    void findByIdWillReturnCourseDto() {
        // Arrange
        Course course = new Course();
        course.setId(1L);
        course.setTitle("title");
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        // Act
        CourseDto actual = underTest.findCourseById(1L);
        // Assert
        assertThat(actual).isNotNull();
        assertThat(actual.title()).isEqualTo(course.getTitle());
        assertThat(actual.id()).isEqualTo(1L);
    }

    @Test
    void findByIdWillThrowResourceNotFoundException() {
        // Assert
        assertThatThrownBy(() -> underTest.findCourseById(Mockito.anyLong())).isInstanceOf(
                ResourceNotFoundException.class
        ).hasMessage("Course Not Found");
    }

    @Test
    void saveCourseWillSaveChangesToDatabase() {
        // Arrange
        CourseDto courseDto = CourseDto.builder()
                .title("title")
                .price(1234.1234)
                .description("description")
                .build();
        ArgumentCaptor<Course> courseCaptor = ArgumentCaptor.forClass(Course.class);
        // Act
        underTest.saveCourse(courseDto);
        // Assert
        verify(courseRepository).save(courseCaptor.capture());
        Course actual = courseCaptor.getValue();
        assertThat(actual).isNotNull();
        assertThat(actual.getDescription()).isEqualTo("description");
        assertThat(actual.getTitle()).isEqualTo("title");
        assertThat(actual.getPrice()).isEqualTo(BigDecimal.valueOf(1234.12));
    }

    @Test
    void saveCourseWillThrowExceptionWhenCourseDtoHasId() {
        // Arrange
        CourseDto courseDto = CourseDto.builder()
                .id(1L)
                .build();
        // Act

        // Assert
        assertThatThrownBy(() -> underTest.saveCourse(courseDto))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("Course has an Id");
    }

    @Test
    void updateCourseWillUpdate() {
        // Arrange
        CourseDto courseDto = CourseDto.builder()
                .id(1L)
                .title("new title")
                .description("new description")
                .price(12.1234)
                .studentNumber(12)
                .build();
        Course course = Course.builder()
                .title("old title")
                .description("old desc")
                .build();
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        ArgumentCaptor<Course> courseCaptor = ArgumentCaptor.forClass(Course.class);
        // Act
        underTest.updateCourse(courseDto);
        // Assert
        verify(courseRepository).save(courseCaptor.capture());
        Course actual = courseCaptor.getValue();
        assertThat(actual).isNotNull();
        assertThat(actual.getDescription()).isEqualTo("new description");
        assertThat(actual.getTitle()).isEqualTo("new title");
        assertThat(actual.getPrice()).isEqualTo(BigDecimal.valueOf(12.12));
        assertThat(actual.getStudentsNumber()).isEqualTo(12);
    }

    @Test
    void updateCourseWillThrowExceptionWhenCourseDtoIsNull() {
        // Arrange
        CourseDto courseDto = null;
        // Act
        // Assert
        assertThatThrownBy(() -> underTest.updateCourse(courseDto))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("Course is null");
    }

    @Test
    void updateCourseWillThrowExceptionWhenCourseDtoIdIsNull() {
        // Arrange
        CourseDto courseDto = CourseDto.builder()
                .id(null)
                .build();
        // Act
        // Assert
        assertThatThrownBy(() -> underTest.updateCourse(courseDto))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("Course is null");
    }

    @Test
    void updateCourseWillThrowExceptionWhenCourseIdNotFound() {
        // Arrange
        CourseDto courseDto = CourseDto.builder()
                .id(1L)
                .build();
        // Act
        // Assert
        assertThatThrownBy(() -> underTest.updateCourse(courseDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Course Not Found");
    }

    @Test
    void deleteCourseWillDeleteIfIdIsPresent() {
        // Arrange
        when(courseRepository.existsById(1L)).thenReturn(true);
        // Act
        underTest.deleteCourse(1L);
        // Assert
        verify(courseRepository).deleteById(1L);
    }

    @Test
    void deleteCourse_shouldNotDeleteWhenCourseDoesNotExist() {
        // Arrange
        long courseId = 1L;
        when(courseRepository.existsById(courseId)).thenReturn(false);

        // Act
        underTest.deleteCourse(courseId);

        // Assert
        verify(courseRepository, never()).deleteById(anyLong());
    }
}
