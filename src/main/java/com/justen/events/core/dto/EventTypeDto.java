package com.justen.events.core.dto;

import java.util.UUID;

import com.justen.events.domain.entity.EventType;

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
public class EventTypeDto {

	private UUID id;

	private String name;

	public EventTypeDto(EventType entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
		}
	}

}
