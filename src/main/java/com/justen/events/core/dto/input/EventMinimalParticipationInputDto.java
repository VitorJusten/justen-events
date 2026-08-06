package com.justen.events.core.dto.input;

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
public class EventMinimalParticipationInputDto {

	private List<UUID> eventIds;
	private Integer quantityEvents;
	private Integer minimalPosition;
	private UUID eventCategoryId;
	
}
