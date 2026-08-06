package com.justen.events.core.dto.input;

import java.util.List;
import java.util.UUID;

import com.justen.events.domain.entity.Event;
import com.justen.events.domain.entity.EventCategory;
import com.justen.events.domain.entity.EventCategoryResult;
import com.justen.events.domain.entity.EventCategoryScore;
import com.justen.events.domain.entity.EventMinimalParticipation;

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

	public EventCategory toEntity() {
		EventCategory category = new EventCategory();
		category.setName(this.name);
		category.setOrder(this.order);
		category.setParticipantsLimit(this.participantsLimit);
		if (this.eventId != null) {
			Event event = new Event();
			event.setId(this.eventId);
			category.setEvent(event);
		}
		if (this.participants != null) {
			category.setParticipants(this.participants.stream().map(EventParticipantInputDto::toEntity).toList());
		}
		if (this.teams != null) {
			category.setTeams(this.teams.stream().map(TeamInputDto::toEntity).toList());
		}
		if (this.minimalParticipation != null) {
			EventMinimalParticipation emp = this.minimalParticipation.toEntity();
			emp.setEventCategory(category);
			category.setMinimalParticipation(emp);
		}
		if (this.scores != null) {
			category.setScores(this.scores.stream().map(s -> {
				EventCategoryScore score = s.toEntity();
				score.setCategory(category);
				return score;
			}).toList());
		}
		if (this.results != null) {
			category.setResults(this.results.stream().map(r -> {
				EventCategoryResult result = r.toEntity();
				result.setCategory(category);
				return result;
			}).toList());
		}
		return category;
	}
	
}
