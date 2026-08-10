package com.justen.events.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.justen.events.domain.entity.Team;

/**
 *
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
public interface TeamRepository extends JpaRepository<Team, UUID> {

	Page<Team> findByNameContainingIgnoreCase(String name, Pageable pageable);

}