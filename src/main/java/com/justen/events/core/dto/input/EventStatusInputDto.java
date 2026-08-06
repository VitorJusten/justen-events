package com.justen.events.core.dto.input;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.justen.events.core.enums.EventStatusEnum;

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
	
}
