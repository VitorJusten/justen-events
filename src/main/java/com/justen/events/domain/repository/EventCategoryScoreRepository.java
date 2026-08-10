package com.justen.events.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.justen.events.domain.entity.EventCategoryScore;

/**
 *
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
public interface EventCategoryScoreRepository extends JpaRepository<EventCategoryScore, UUID> {

    Page<EventCategoryScore> findAll(Pageable pageable);
    List<EventCategoryScore> findByCategory_Id(UUID categoryId);

}