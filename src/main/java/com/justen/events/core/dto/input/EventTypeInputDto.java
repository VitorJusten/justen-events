package com.justen.events.core.dto.input;

import java.util.UUID;

import com.justen.events.domain.entity.EventType;

import lombok.Data;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
@Data
public class EventTypeInputDto {

	private UUID id;

	private String name;

	public EventType toEntity() {
		EventType eventType = new EventType();
		eventType.setId(this.id);
		eventType.setName(this.name);
		return eventType;
	}
	
}
