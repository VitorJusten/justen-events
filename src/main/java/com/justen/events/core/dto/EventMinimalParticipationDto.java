package com.justen.events.core.dto;

import java.util.UUID;

import com.justen.events.domain.entity.EventMinimalParticipation;

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
public class EventMinimalParticipationDto {

	private UUID id;

	private Integer quantityEvents;

	private Integer minimalPosition;

	private UUID eventCategoryId;

	public EventMinimalParticipationDto(EventMinimalParticipation entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.quantityEvents = entity.getQuantityEvents();
			this.minimalPosition = entity.getMinimalPosition();
			if (entity.getEventCategory() != null) {
				this.eventCategoryId = entity.getEventCategory().getId();
			}
		}
	}

}
