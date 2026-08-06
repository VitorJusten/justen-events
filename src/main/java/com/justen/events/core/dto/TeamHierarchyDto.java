package com.justen.events.core.dto;

import java.util.UUID;

import com.justen.events.core.enums.TeamRoleEnum;
import com.justen.events.core.types.TeamHierarchyId;
import com.justen.events.domain.entity.Team;
import com.justen.events.domain.entity.TeamHierarchy;

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
public class TeamHierarchyDto {

	private TeamHierarchyId id;

	private Team team;

	private UUID userId;

	private TeamRoleEnum role;

	public TeamHierarchyDto(TeamHierarchy entity) {
		if (entity != null) {
			if (entity.getId() != null) {
				this.id = new TeamHierarchyId();
				this.id.setUserId(entity.getId().getUserId());
				this.id.setTeamId(entity.getId().getTeamId());
			}
			this.team = entity.getTeam();
			this.userId = entity.getUserId();
			this.role = entity.getRole();
		}
	}

}
