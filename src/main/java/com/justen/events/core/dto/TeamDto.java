package com.justen.events.core.dto;

import java.util.List;
import java.util.UUID;

import com.justen.events.domain.entity.Team;

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
public class TeamDto {

	private UUID id;

	private String name;

	private String metadata;
	
	private List<TeamHierarchyDto> teamHierarchy;

	public TeamDto(Team entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.metadata = entity.getMetadata();
			if (entity.getTeamHierarchy() != null) {
				this.teamHierarchy = entity.getTeamHierarchy().stream().map(TeamHierarchyDto::new).toList();
			}
		}
	}
	
}
