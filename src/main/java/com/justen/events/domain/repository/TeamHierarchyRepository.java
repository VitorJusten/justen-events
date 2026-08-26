package com.justen.events.domain.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.justen.events.core.enums.HierarchyStatusEnum;
import com.justen.events.core.enums.TeamRoleEnum;
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

	Optional<TeamHierarchy> findByTeam_IdAndUserId(UUID teamId, UUID userId);

	List<TeamHierarchy> findByTeam_IdAndStatus(UUID teamId, HierarchyStatusEnum status);

	List<TeamHierarchy> findByUserIdAndStatus(UUID userId, HierarchyStatusEnum status);

	boolean existsByTeam_IdAndUserIdAndRoleInAndStatus(UUID teamId, UUID userId, Collection<TeamRoleEnum> roles,
			HierarchyStatusEnum status);

	boolean existsByTeam_IdAndUserIdAndStatus(UUID teamId, UUID userId, HierarchyStatusEnum status);

}