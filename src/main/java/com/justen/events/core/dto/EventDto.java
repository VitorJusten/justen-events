package com.justen.events.core.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.justen.events.core.enums.EventStatusEnum;
import com.justen.events.domain.entity.EventCategory;
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
public class EventDto {

	private UUID id;

	private String name;

	private String description;

	private EventStatusEnum status;

	private OffsetDateTime startDate;

	private OffsetDateTime endDate;

	private byte[] regulationFile;

	private EventType type;

	private List<EventCategory> categories;

	private List<EventDto> subevents;

	private OffsetDateTime createdAt;

	private OffsetDateTime updatedAt;
	
}
