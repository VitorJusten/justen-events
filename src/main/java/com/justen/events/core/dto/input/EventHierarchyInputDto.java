package com.justen.events.core.dto.input;

import java.util.UUID;

import com.justen.events.core.enums.EventRoleEnum;
import com.justen.events.core.enums.HierarchyStatusEnum;
import com.justen.events.core.types.EventHierarchyId;
import com.justen.events.domain.entity.Event;
import com.justen.events.domain.entity.EventHierarchy;

import lombok.Data;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
@Data
public class EventHierarchyInputDto {

	private UUID eventId;

	private UUID userId;

	private EventRoleEnum role;

	private HierarchyStatusEnum status;

	public EventHierarchy toEntity() {
		EventHierarchy entity = new EventHierarchy();
		EventHierarchyId id = new EventHierarchyId();
		id.setEventId(this.eventId);
		id.setUserId(this.userId);
		entity.setId(id);
		entity.setUserId(this.userId);
		entity.setRole(this.role);
		entity.setStatus(this.status != null ? this.status : HierarchyStatusEnum.USER_DECISION);
		if (this.eventId != null) {
			Event event = new Event();
			event.setId(this.eventId);
			entity.setEvent(event);
		}
		return entity;
	}

}
