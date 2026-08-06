package com.justen.events.domain.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.justen.events.core.enums.EventStatusEnum;

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
@Table(name = "event_status")
public class EventStatus {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "evst_cd_id", nullable = false)
	private UUID id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "even_cd_id")
	private Event event;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "evst_tx_status", nullable = false)
	private EventStatusEnum status;
	
	@Column(name = "evst_dt_start_date", nullable = false)
	private OffsetDateTime startDate;
	
	@Column(name = "evst_dt_finish_date", nullable = false)
	private OffsetDateTime finishDate;
	
}
