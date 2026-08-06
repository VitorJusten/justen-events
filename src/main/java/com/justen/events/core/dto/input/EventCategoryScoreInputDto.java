package com.justen.events.core.dto.input;

import java.math.BigDecimal;
import java.util.UUID;

import com.justen.events.core.enums.ParticipantTypeEnum;
import com.justen.events.domain.entity.EventCategory;
import com.justen.events.domain.entity.EventCategoryScore;

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

	public EventCategoryScore toEntity() {
		EventCategoryScore score = new EventCategoryScore();
		score.setPosition(this.position);
		score.setPoints(this.points);
		score.setScoreType(this.scoreType);
		if (this.categoryId != null) {
			EventCategory category = new EventCategory();
			category.setId(this.categoryId);
			score.setCategory(category);
		}
		return score;
	}
	
}
