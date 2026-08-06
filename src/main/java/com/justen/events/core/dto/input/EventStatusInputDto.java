package com.justen.events.core.dto.input;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.justen.events.core.enums.EventStatusEnum;
import com.justen.events.domain.entity.Event;
import com.justen.events.domain.entity.EventStatus;

import lombok.Data;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
@Data
public class EventStatusInputDto {

	private EventStatusEnum status;
	
	private OffsetDateTime startDate;
	
	private OffsetDateTime finishDate;
	
	private UUID eventId;

	public EventStatus toEntity() {
		EventStatus eventStatus = new EventStatus();
		eventStatus.setStatus(this.status);
		eventStatus.setStartDate(this.startDate);
		eventStatus.setFinishDate(this.finishDate);
		if (this.eventId != null) {
			Event event = new Event();
			event.setId(this.eventId);
			eventStatus.setEvent(event);
		}
		return eventStatus;
	}
	
}
