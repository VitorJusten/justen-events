package com.justen.events.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.justen.events.domain.entity.EventCategory;

/**
 *
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
public interface EventCategoryRepository extends JpaRepository<EventCategory, UUID> {

    Page<EventCategory> findByNameContainingIgnoreCase(String name, Pageable pageable);
    List<EventCategory> findByEvent_Id(UUID eventId);
    List<EventCategory> findByParticipants_Id(UUID participantId);

}