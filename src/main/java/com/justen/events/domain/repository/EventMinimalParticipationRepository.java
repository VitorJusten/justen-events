package com.justen.events.domain.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.justen.events.domain.entity.EventMinimalParticipation;

/**
 *
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
public interface EventMinimalParticipationRepository extends JpaRepository<EventMinimalParticipation, UUID> {

    Page<EventMinimalParticipation> findAll(Pageable pageable);

}