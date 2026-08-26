package com.justen.events.core.dto;

import java.util.UUID;

import com.justen.events.core.enums.EventRoleEnum;
import com.justen.events.core.enums.HierarchyStatusEnum;
import com.justen.events.core.types.EventHierarchyId;
import com.justen.events.domain.entity.EventHierarchy;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
@Data
@NoArgsConstructor
public class EventHierarchyDto {

	private EventHierarchyId id;

	private UUID eventId;

	private UUID userId;

	private EventRoleEnum role;

	private HierarchyStatusEnum status;

	public EventHierarchyDto(EventHierarchy entity) {
		if (entity != null) {
			if (entity.getId() != null) {
				this.id = new EventHierarchyId();
				this.id.setUserId(entity.getId().getUserId());
				this.id.setEventId(entity.getId().getEventId());
			}
			if (entity.getEvent() != null) {
				this.eventId = entity.getEvent().getId();
			}
			this.userId = entity.getUserId();
			this.role = entity.getRole();
			this.status = entity.getStatus();
		}
	}

}
