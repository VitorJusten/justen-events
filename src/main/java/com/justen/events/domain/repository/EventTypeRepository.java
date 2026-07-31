package com.justen.events.domain.repository;

import java.util.UUID;

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

}