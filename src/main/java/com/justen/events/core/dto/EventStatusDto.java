package com.justen.events.core.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.justen.events.core.enums.EventStatusEnum;
import com.justen.events.domain.entity.Event;
import com.justen.events.domain.entity.EventStatus;

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
public class EventStatusDto {

	private UUID id;

	private Event event;

	private EventStatusEnum status;

	private OffsetDateTime startDate;

	private OffsetDateTime finishDate;

	public EventStatusDto(EventStatus entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.event = entity.getEvent();
			this.status = entity.getStatus();
			this.startDate = entity.getStartDate();
			this.finishDate = entity.getFinishDate();
		}
	}

}
