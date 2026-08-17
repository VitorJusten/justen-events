package com.justen.events.domain.entity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
@Table(name = "event")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@EqualsAndHashCode.Include
	@Column(name = "even_cd_id")
	private UUID id;

	@Column(name = "even_tx_name", nullable = false)
	private String name;

	@Column(name = "even_tx_description")
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "evmp_cd_id")
	private EventMinimalParticipation eventMinimalParticipation;

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EventStatus> status;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "even_bt_regulation", nullable = true)
	private byte[] regulationFile;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "evty_cd_id", nullable = false)
	private EventType type;

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EventCategory> categories;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Event> subevents;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "even_cd_parent")
	private Event parent;

	@Column(name = "even_dt_created_at", nullable = false)
	private OffsetDateTime createdAt;

	@Column(name = "even_dt_updated_at")
	private OffsetDateTime updatedAt;

	public EventStatus getCurrentStatus() {
		OffsetDateTime now = OffsetDateTime.now();

		return this.status.stream().filter(s -> now.isAfter(s.getStartDate()) && now.isBefore(s.getFinishDate()))
				.findFirst().orElse(null);
	}
}