package com.justen.events.domain.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.justen.events.core.enums.EventRoleEnum;
import com.justen.events.core.enums.HierarchyStatusEnum;
import com.justen.events.core.types.EventHierarchyId;
import com.justen.events.domain.entity.EventHierarchy;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
public interface EventHierarchyRepository extends JpaRepository<EventHierarchy, EventHierarchyId> {

	Page<EventHierarchy> findAll(Pageable pageable);

	List<EventHierarchy> findByEvent_Id(UUID eventId);

	List<EventHierarchy> findByUserId(UUID userId);

	Optional<EventHierarchy> findByEvent_IdAndUserId(UUID eventId, UUID userId);

	List<EventHierarchy> findByEvent_IdAndStatus(UUID eventId, HierarchyStatusEnum status);

	List<EventHierarchy> findByUserIdAndStatus(UUID userId, HierarchyStatusEnum status);

	boolean existsByEvent_IdAndUserIdAndRoleInAndStatus(UUID eventId, UUID userId, Collection<EventRoleEnum> roles,
			HierarchyStatusEnum status);

	boolean existsByEvent_IdAndUserIdAndRoleAndStatus(UUID eventId, UUID userId, EventRoleEnum role,
			HierarchyStatusEnum status);

	boolean existsByEvent_IdAndUserIdAndStatus(UUID eventId, UUID userId, HierarchyStatusEnum status);

}
