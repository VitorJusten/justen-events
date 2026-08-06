package com.justen.events.core.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.justen.events.domain.entity.Event;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
@Data
@NoArgsConstructor
public class EventDto {

	private UUID id;

	private String name;

	private String description;

	private List<EventStatusDto> status;

	private byte[] regulationFile;

	private EventTypeDto type;

	private List<EventCategoryDto> categories;

	private List<EventDto> subevents;

	private UUID parentId;

	private OffsetDateTime createdAt;

	private OffsetDateTime updatedAt;

	public EventDto(Event entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.description = entity.getDescription();
			this.regulationFile = entity.getRegulationFile();
			if (entity.getStatus() != null) {
				this.status = entity.getStatus().stream().map(EventStatusDto::new).toList();
			}
			if (entity.getType() != null) {
				this.type = new EventTypeDto(entity.getType());
			}
			if (entity.getCategories() != null) {
				this.categories = entity.getCategories().stream().map(EventCategoryDto::new).toList();
			}
			if (entity.getSubevents() != null) {
				this.subevents = entity.getSubevents().stream().map(EventDto::new).toList();
			}
			if (entity.getParent() != null) {
				this.parentId = entity.getParent().getId();
			}
			this.createdAt = entity.getCreatedAt();
			this.updatedAt = entity.getUpdatedAt();
		}
	}

}
