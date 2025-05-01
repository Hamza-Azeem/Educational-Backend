package com.education.educational_platform.base;

import java.util.List;

public interface BaseService<E, I> {
    List<E> findAll();
    E findById(I id);
    void delete(I id);
}
