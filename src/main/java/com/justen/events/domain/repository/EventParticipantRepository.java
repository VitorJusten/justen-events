package com.justen.events.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<EventParticipant> findByNameContainingIgnoreCase(String name, Pageable pageable);
    List<EventParticipant> findByTeam_Id(UUID teamId);

}