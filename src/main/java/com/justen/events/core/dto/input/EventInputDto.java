package com.justen.events.core.dto.input;

import java.time.OffsetDateTime;
import java.util.List;
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
public class EventInputDto {

	private String name;

	private String description;

	private EventStatusEnum status;

	private OffsetDateTime startDate;

	private OffsetDateTime endDate;

	private byte[] regulationFile;

	private UUID eventTypeId;

	private List<EventCategoryInputDto> categories;
	
	private UUID parentEvent;

}
