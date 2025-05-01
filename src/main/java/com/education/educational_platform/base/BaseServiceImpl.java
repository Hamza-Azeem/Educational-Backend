package com.education.educational_platform.base;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseServiceImpl<E, I> implements BaseService<E, I> {

    @Override
    public List<E> findAll() {
        return List.of();
    }

    @Override
    public E findById(I id) {
        return null;
    }

    @Override
    public void delete(I id) {

    }
}
