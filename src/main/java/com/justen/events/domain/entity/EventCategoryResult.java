package com.justen.events.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.justen.events.core.enums.EventResultStatusEnum;
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
@Table(name = "event_category_result")
public class EventCategoryResult {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@EqualsAndHashCode.Include
	@Column(name = "evcr_cd_id")
	private UUID id;

	@Enumerated(EnumType.STRING)
	@Column(name = "evcr_tx_score_type", nullable = false, length = 20)
	private ParticipantTypeEnum scoreType;

	@Column(name = "usac_cd_id")
	private UUID userId;

	@Column(name = "evcr_tx_participant_name", length = 150)
	private String participantName;

	@Column(name = "team_cd_id")
	private UUID teamId;

	@Column(name = "evcr_tx_team_name", length = 150)
	private String teamName;

	@Column(name = "evcr_nm_position", nullable = false)
	private Integer position;

	@Column(name = "evcr_nm_points", nullable = false, precision = 10, scale = 2)
	private BigDecimal points;

	@Enumerated(EnumType.STRING)
	@Column(name = "evcr_tx_status", nullable = false, length = 20)
	private EventResultStatusEnum status;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "evcr_js_metadata", columnDefinition = "jsonb")
	private String metadata;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "evct_cd_id", nullable = false)
	private EventCategory category;

}