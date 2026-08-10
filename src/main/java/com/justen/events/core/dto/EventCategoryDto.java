package com.justen.events.core.dto;

import java.util.List;
import java.util.UUID;

import com.justen.events.domain.entity.EventCategory;

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
public class EventCategoryDto {

	private UUID id;

	private String name;

	private Integer order;

	private Integer participantsLimit;

	private List<EventParticipantDto> participants;

	private List<TeamDto> teams;

	private EventMinimalParticipationDto minimalParticipation;

	private List<EventCategoryScoreDto> scores;

	private List<EventCategoryResultDto> results;

	private UUID eventId;

	public EventCategoryDto(EventCategory entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.order = entity.getOrder();
			this.participantsLimit = entity.getParticipantsLimit();
			if (entity.getParticipants() != null) {
				this.participants = entity.getParticipants().stream().map(EventParticipantDto::new).toList();
			}
			if (entity.getTeams() != null) {
				this.teams = entity.getTeams().stream().map(TeamDto::new).toList();
			}
			if (entity.getMinimalParticipation() != null) {
				this.minimalParticipation = new EventMinimalParticipationDto(entity.getMinimalParticipation());
			}
			if (entity.getScores() != null) {
				this.scores = entity.getScores().stream().map(EventCategoryScoreDto::new).toList();
			}
			if (entity.getResults() != null) {
				this.results = entity.getResults().stream().map(EventCategoryResultDto::new).toList();
			}
			if (entity.getEvent() != null) {
				this.eventId = entity.getEvent().getId();
			}
		}
	}

}
