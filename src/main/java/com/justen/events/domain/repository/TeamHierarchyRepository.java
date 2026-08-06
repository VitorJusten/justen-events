package com.justen.events.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justen.events.core.types.TeamHierarchyId;
import com.justen.events.domain.entity.TeamHierarchy;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
public interface TeamHierarchyRepository extends JpaRepository<TeamHierarchy, TeamHierarchyId> {

}