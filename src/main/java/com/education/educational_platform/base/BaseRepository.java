package com.education.educational_platform.base;

import org.springframework.data.jpa.repository.JpaRepository;

// E for entity - I for ID
public interface BaseRepository<E, I> extends JpaRepository<E, I> {
}
