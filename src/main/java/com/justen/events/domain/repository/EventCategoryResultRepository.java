package com.justen.events.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.justen.events.domain.entity.EventCategoryResult;

/**
 *
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
public interface EventCategoryResultRepository extends JpaRepository<EventCategoryResult, UUID> {

    Page<EventCategoryResult> findAll(Pageable pageable);
    List<EventCategoryResult> findByCategory_Id(UUID categoryId);

}