package com.justen.events.domain.entity;

import java.util.UUID;

import com.justen.events.core.enums.EventRoleEnum;
import com.justen.events.core.enums.HierarchyStatusEnum;
import com.justen.events.core.types.EventHierarchyId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "event_hierarchy")
public class EventHierarchy {

	@EmbeddedId
	@EqualsAndHashCode.Include
	private EventHierarchyId id;

	@MapsId("eventId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "even_cd_id", nullable = false)
	private Event event;

	@Column(name = "usac_cd_id", nullable = false)
	private UUID userId;

	@Enumerated(EnumType.STRING)
	@Column(name = "evhi_tx_role", nullable = false)
	private EventRoleEnum role;

	@Enumerated(EnumType.STRING)
	@Column(name = "evhi_tx_status", nullable = false)
	private HierarchyStatusEnum status;

}
