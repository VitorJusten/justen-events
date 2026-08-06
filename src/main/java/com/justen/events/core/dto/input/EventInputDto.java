package com.justen.events.core.dto.input;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

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
	private List<EventStatusInputDto> status;
	private byte[] regulationFile;
	private EventTypeInputDto type;
	private List<EventCategoryInputDto> categories;
	private UUID parentId;
	private OffsetDateTime createdAt;
	private OffsetDateTime updatedAt;

}
