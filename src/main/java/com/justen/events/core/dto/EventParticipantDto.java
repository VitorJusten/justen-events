package com.justen.events.core.dto;

import java.util.UUID;

import com.justen.events.domain.entity.EventParticipant;

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
public class EventParticipantDto {

	private UUID id;

	private UUID userId;

	private String name;

	private TeamDto team;

	private String metadata;

	public EventParticipantDto(EventParticipant entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.userId = entity.getUserId();
			this.name = entity.getName();
			if (entity.getTeam() != null) {
				this.team = new TeamDto(entity.getTeam());
			}
			this.metadata = entity.getMetadata();
		}
	}
	
}
