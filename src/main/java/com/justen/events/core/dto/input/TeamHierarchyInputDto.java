package com.justen.events.core.dto.input;

import java.util.UUID;

import com.justen.events.core.enums.TeamRoleEnum;
import com.justen.events.core.types.TeamHierarchyId;
import com.justen.events.domain.entity.Team;
import com.justen.events.domain.entity.TeamHierarchy;

import lombok.Data;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
@Data
public class TeamHierarchyInputDto {

	private UUID teamId;

	private UUID userId;

	private TeamRoleEnum role;

	public TeamHierarchy toEntity() {
		TeamHierarchy entity = new TeamHierarchy();
		TeamHierarchyId id = new TeamHierarchyId();
		id.setTeamId(this.teamId);
		id.setUserId(this.userId);
		entity.setId(id);
		entity.setUserId(this.userId);
		entity.setRole(this.role);
		if (this.teamId != null) {
			Team team = new Team();
			team.setId(this.teamId);
			entity.setTeam(team);
		}
		return entity;
	}

}
