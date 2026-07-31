package com.justen.events.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justen.events.domain.entity.TeamHierarchy;
import com.justen.events.domain.entity.TeamHierarchyId;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
public interface TeamHierarchyRepository extends JpaRepository<TeamHierarchy, TeamHierarchyId> {

}