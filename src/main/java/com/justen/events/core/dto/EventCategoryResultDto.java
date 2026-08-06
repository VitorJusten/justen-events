package com.justen.events.core.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.justen.events.core.enums.EventResultStatusEnum;
import com.justen.events.core.enums.ParticipantTypeEnum;
import com.justen.events.domain.entity.EventCategoryResult;

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
public class EventCategoryResultDto {

	private UUID id;

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

	public EventCategoryResultDto(EventCategoryResult entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.scoreType = entity.getScoreType();
			this.userId = entity.getUserId();
			this.participantName = entity.getParticipantName();
			this.teamId = entity.getTeamId();
			this.teamName = entity.getTeamName();
			this.position = entity.getPosition();
			this.points = entity.getPoints();
			this.status = entity.getStatus();
			this.metadata = entity.getMetadata();
			if (entity.getCategory() != null) {
				this.categoryId = entity.getCategory().getId();
			}
		}
	}

}
