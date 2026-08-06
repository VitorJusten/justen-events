package com.justen.events.core.dto.input;

import java.util.UUID;

import com.justen.events.core.enums.TeamRoleEnum;
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
		TeamHierarchy t = new TeamHierarchy();
		t.setRole(this.role);
		t.setUserId(userId);
		
		Team team = new Team();
		team.setId(this.teamId);
		t.setTeam(team);
		
		return t;
	}
	
}
