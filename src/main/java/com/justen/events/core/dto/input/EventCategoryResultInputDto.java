package com.justen.events.core.dto.input;

import java.math.BigDecimal;
import java.util.UUID;

import com.justen.events.core.enums.EventResultStatusEnum;
import com.justen.events.core.enums.ParticipantTypeEnum;

import lombok.Data;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
@Data
public class EventCategoryResultInputDto {

	private ParticipantTypeEnum scoreType;
	private UUID userId;
	private String participantName;
	private UUID teamId;
	private String teamName;
	private Integer position;
	private BigDecimal points;
	private EventResultStatusEnum status;
	private String metadata;
	private UUID categoryId;
	
}
