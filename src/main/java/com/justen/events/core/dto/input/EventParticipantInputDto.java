package com.justen.events.core.dto.input;

import java.util.UUID;

import com.justen.events.domain.entity.EventParticipant;
import com.justen.events.domain.entity.Team;

import lombok.Data;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
@Data
public class EventParticipantInputDto {

	private UUID userId;
	private String name;
	private UUID teamId;
	
	//JSON
	private String metadata;

	public EventParticipant toEntity() {
		EventParticipant participant = new EventParticipant();
		participant.setUserId(this.userId);
		participant.setName(this.name);
		participant.setMetadata(this.metadata);
		if (this.teamId != null) {
			Team team = new Team();
			team.setId(this.teamId);
			participant.setTeam(team);
		}
		return participant;
	}
			
}
