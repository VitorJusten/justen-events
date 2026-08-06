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

	public com.justen.events.domain.entity.EventCategoryResult toEntity() {
		com.justen.events.domain.entity.EventCategoryResult result = new com.justen.events.domain.entity.EventCategoryResult();
		result.setScoreType(this.scoreType);
		result.setUserId(this.userId);
		result.setParticipantName(this.participantName);
		result.setTeamId(this.teamId);
		result.setTeamName(this.teamName);
		result.setPosition(this.position);
		result.setPoints(this.points);
		result.setStatus(this.status);
		result.setMetadata(this.metadata);
		if (this.categoryId != null) {
			com.justen.events.domain.entity.EventCategory category = new com.justen.events.domain.entity.EventCategory();
			category.setId(this.categoryId);
			result.setCategory(category);
		}
		return result;
	}
	
}
