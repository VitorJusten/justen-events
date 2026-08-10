package com.justen.events.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.justen.events.domain.entity.EventStatus;

/**
 *
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
public interface EventStatusRepository extends JpaRepository<EventStatus, UUID> {

    Page<EventStatus> findAll(Pageable pageable);
    List<EventStatus> findByEvent_Id(UUID eventId);

}
