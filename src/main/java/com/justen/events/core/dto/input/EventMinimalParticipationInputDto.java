package com.justen.events.core.dto.input;

import java.util.List;
import java.util.UUID;

import com.justen.events.domain.entity.Event;
import com.justen.events.domain.entity.EventCategory;
import com.justen.events.domain.entity.EventMinimalParticipation;

import lombok.Data;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
@Data
public class EventMinimalParticipationInputDto {

	private List<UUID> eventIds;
	private Integer quantityEvents;
	private Integer minimalPosition;
	private UUID eventCategoryId;

	public EventMinimalParticipation toEntity() {
		EventMinimalParticipation emp = new EventMinimalParticipation();
		emp.setQuantityEvents(this.quantityEvents);
		emp.setMinimalPosition(this.minimalPosition);
		if (this.eventCategoryId != null) {
			EventCategory category = new EventCategory();
			category.setId(this.eventCategoryId);
			emp.setEventCategory(category);
		}
		if (this.eventIds != null) {
			emp.setEvents(this.eventIds.stream().map(id -> {
				Event event = new Event();
				event.setId(id);
				return event;
			}).toList());
		}
		return emp;
	}
	
}
