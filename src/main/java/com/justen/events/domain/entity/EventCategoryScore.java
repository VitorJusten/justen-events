package com.justen.events.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

import com.justen.events.core.enums.ParticipantTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "event_category_score")
public class EventCategoryScore {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@EqualsAndHashCode.Include
	@Column(name = "evcs_cd_id")
	private UUID id;

	@Column(name = "evcs_nm_position", nullable = false)
	private Integer position;

	@Column(name = "evcs_nm_points", nullable = false, precision = 10, scale = 2)
	private BigDecimal points;

	@Enumerated(EnumType.STRING)
	@Column(name = "evcs_tx_score_type", nullable = false, length = 20)
	private ParticipantTypeEnum scoreType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "evct_cd_id", nullable = false)
	private EventCategory category;

}