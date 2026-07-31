package com.justen.events.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justen.events.domain.entity.EventParticipant;

/**
 *
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
public interface EventParticipantRepository extends JpaRepository<EventParticipant, UUID> {

}