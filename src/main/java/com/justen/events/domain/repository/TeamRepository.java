package com.justen.events.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justen.events.domain.entity.Team;

/**
 *
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
public interface TeamRepository extends JpaRepository<Team, UUID> {

}