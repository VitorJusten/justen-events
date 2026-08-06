package com.justen.events.core.dto.input;

import java.math.BigDecimal;
import java.util.UUID;

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
public class EventCategoryScoreInputDto {

	private Integer position;
	private BigDecimal points;
	private ParticipantTypeEnum scoreType;
	private UUID categoryId;
	
}
