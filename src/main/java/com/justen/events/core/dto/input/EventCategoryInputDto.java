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
public class EventCategoryInputDto {

	private String name;
	private Integer order;
	private Integer participantsLimit;
	private List<EventParticipantInputDto> participants;
	private List<TeamInputDto> teams;
	private EventMinimalParticipationInputDto minimalParticipation;
	private List<EventCategoryScoreInputDto> scores;
	private List<EventCategoryResultInputDto> results;
	private UUID eventId;
	
}
