package com.justen.events.core.dto.input;

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
public class EventParticipantInputDto {

	private UUID userId;
	private String name;
	private UUID teamId;
	
	//JSON
	private String metadata;
			
}
