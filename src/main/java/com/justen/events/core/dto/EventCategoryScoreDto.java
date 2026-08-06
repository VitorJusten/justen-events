package com.justen.events.core.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.justen.events.core.enums.ParticipantTypeEnum;
import com.justen.events.domain.entity.EventCategoryScore;

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
public class EventCategoryScoreDto {

	private UUID id;

	private Integer position;

	private BigDecimal points;

	private ParticipantTypeEnum scoreType;

	private UUID categoryId;

	public EventCategoryScoreDto(EventCategoryScore entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.position = entity.getPosition();
			this.points = entity.getPoints();
			this.scoreType = entity.getScoreType();
			if (entity.getCategory() != null) {
				this.categoryId = entity.getCategory().getId();
			}
		}
	}
	
}
