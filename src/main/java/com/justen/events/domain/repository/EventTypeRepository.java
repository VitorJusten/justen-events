package com.justen.events.domain.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.justen.events.domain.entity.EventType;

/**
 *
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
public interface EventTypeRepository extends JpaRepository<EventType, UUID> {

    Page<EventType> findByNameContainingIgnoreCase(String name, Pageable pageable);

}