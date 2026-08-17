package com.justen.events.domain.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "event_minimal_participation")
public class EventMinimalParticipation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@EqualsAndHashCode.Include
	@Column(name = "evmp_cd_id")
	private UUID id;
	
	@OneToMany(
			mappedBy = "eventMinimalParticipation",
			fetch = FetchType.LAZY)
	private List<Event> events;

	@Column(name = "evmp_nm_quantity_events", nullable = false)
	private Integer quantityEvents;

	@Column(name = "evmp_nm_minimal_position", nullable = false)
	private Integer minimalPosition;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "evca_cd_id", nullable = false)
	private EventCategory eventCategory;

}