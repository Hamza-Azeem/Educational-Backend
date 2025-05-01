package com.education.educational_platform.service.impl;

import com.education.educational_platform.dto.CourseDto;
import com.education.educational_platform.entity.Course;
import com.education.educational_platform.exception.InvalidRequestException;
import com.education.educational_platform.exception.ResourceNotFoundException;
import com.education.educational_platform.mapper.CourseMapper;
import com.education.educational_platform.repository.CourseRepository;
import com.education.educational_platform.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

import static com.education.educational_platform.mapper.CourseMapper.toCourse;
import static com.education.educational_platform.mapper.CourseMapper.toCourseDto;

@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Override
    public List<CourseDto> findAll(int pageNumber, int size, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sortBy));
        Page<Course> courses = courseRepository.findAll(pageable);
        return courses.stream().map(CourseMapper::toCourseDto).toList();
    }

    @Override
    public CourseDto findCourseById(long id) {
        Course course = courseRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Course Not Found"));
        return toCourseDto(course);
    }

    @Override
    public void saveCourse(CourseDto courseDto) {
        if(courseDto.id() != null){
            throw new InvalidRequestException("Course has an Id");
        }
        Course course = toCourse(courseDto);
        courseRepository.save(course);
    }

    @Override
    public void updateCourse(CourseDto courseDto) {
        if(courseDto == null || courseDto.id() == null)
            throw new InvalidRequestException("Course is null");
        Course course = courseRepository.findById(courseDto.id()).orElseThrow(()
                -> new ResourceNotFoundException("Course Not Found"));
        if(courseDto.title() != null && !courseDto.title().isBlank()){
            course.setTitle(courseDto.title());
        }
        if(courseDto.description() != null && !courseDto.description().isBlank()){
            course.setDescription(courseDto.description());
        }
        if(courseDto.price() != null && courseDto.price() > 0){
            course.setPrice(BigDecimal.valueOf(courseDto.price()).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if(courseDto.studentNumber() != null && courseDto.studentNumber() > 0){
            course.setStudentsNumber(courseDto.studentNumber());
        }
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(long id) {
        if(courseRepository.existsById(id)){
            courseRepository.deleteById(id);
        }
    }

}
