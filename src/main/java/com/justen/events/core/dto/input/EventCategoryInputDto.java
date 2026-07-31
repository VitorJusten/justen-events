package com.justen.events.core.dto.input;

import java.util.List;

import com.justen.events.domain.entity.Event;
import com.justen.events.domain.entity.EventCategoryResult;
import com.justen.events.domain.entity.EventCategoryScore;
import com.justen.events.domain.entity.EventMinimalParticipation;
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
//TODO TERMINAR IMPLEMENTAÇÃO
@Data
public class EventCategoryInputDto {

	private String name;

	private Integer order;

	private Integer participantsLimit;

	private List<EventParticipant> participants;

	private List<Team> teams;

	private EventMinimalParticipation minimalParticipation;

	private List<EventCategoryScore> scores;

	private List<EventCategoryResult> results;

	private Event event;
	
}
