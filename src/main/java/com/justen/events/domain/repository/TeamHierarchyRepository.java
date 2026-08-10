package com.justen.events.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<TeamHierarchy> findAll(Pageable pageable);
    List<TeamHierarchy> findByTeam_Id(UUID teamId);
    List<TeamHierarchy> findByUserId(UUID userId);

}