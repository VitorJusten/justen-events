package com.justen.events.domain.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "event_category")
public class EventCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@EqualsAndHashCode.Include
	@Column(name = "evct_cd_id")
	private UUID id;

	@Column(name = "evct_tx_name", nullable = false, length = 100)
	private String name;

	@Column(name = "evct_nm_order", nullable = false)
	private Integer order;

	@Column(name = "evct_nm_participants_limit")
	private Integer participantsLimit;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "event_category_participant",
			joinColumns = @JoinColumn(name = "evct_cd_id"),
			inverseJoinColumns = @JoinColumn(name = "evpt_cd_id"))
	private List<EventParticipant> participants;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "event_category_team",
			joinColumns = @JoinColumn(name = "evct_cd_id"),
			inverseJoinColumns = @JoinColumn(name = "team_cd_id"))
	private List<Team> teams;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "evmp_cd_id")
	private EventMinimalParticipation minimalParticipation;

	@OneToMany(
			mappedBy = "category",
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<EventCategoryScore> scores;

	@OneToMany(
			mappedBy = "category",
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<EventCategoryResult> results;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "even_cd_id", nullable = false)
	private Event event;

}