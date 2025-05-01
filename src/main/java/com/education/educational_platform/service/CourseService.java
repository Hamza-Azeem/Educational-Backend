package com.education.educational_platform.service;

import com.education.educational_platform.dto.CourseDto;
import com.education.educational_platform.entity.Course;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {
    List<CourseDto> findAll(int page, int size, String sortBy);
    CourseDto findCourseById(long id);
    void saveCourse(CourseDto courseDto);
    void updateCourse(CourseDto courseDto);
    void deleteCourse(long id);
}
