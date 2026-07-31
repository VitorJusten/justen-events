package com.justen.events.domain.entity;

import java.util.UUID;

import com.justen.events.core.enums.TeamRoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "team_hierarchy")
public class TeamHierarchy {

	@EmbeddedId
	@EqualsAndHashCode.Include
	private TeamHierarchyId id;


	@MapsId("teamId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_cd_id", nullable = false)
	private Team team;


	@Column(name = "usac_cd_id", nullable = false)
	private UUID userId;


	@Column(name = "tehi_tx_role", nullable = false)
	private TeamRoleEnum role;

}