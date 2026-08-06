package com.justen.events.core.dto.input;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.justen.events.domain.entity.Event;
import com.justen.events.domain.entity.EventCategory;
import com.justen.events.domain.entity.EventStatus;

import lombok.Data;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
@Data
public class EventInputDto {

	private String name;
	private String description;
	private List<EventStatusInputDto> status;
	private byte[] regulationFile;
	private EventTypeInputDto type;
	private List<EventCategoryInputDto> categories;
	private UUID parentId;
	private OffsetDateTime createdAt;
	private OffsetDateTime updatedAt;

	public Event toEntity() {
		Event event = new Event();
		event.setName(this.name);
		event.setDescription(this.description);
		event.setRegulationFile(this.regulationFile);
		if (this.type != null) {
			event.setType(this.type.toEntity());
		}
		if (this.status != null) {
			event.setStatus(this.status.stream().map(s -> {
				EventStatus statusEntity = s.toEntity();
				statusEntity.setEvent(event);
				return statusEntity;
			}).toList());
		}
		if (this.categories != null) {
			event.setCategories(this.categories.stream().map(c -> {
				EventCategory categoryEntity = c.toEntity();
				categoryEntity.setEvent(event);
				return categoryEntity;
			}).toList());
		}
		if (this.parentId != null) {
			Event parentEvent = new Event();
			parentEvent.setId(this.parentId);
			event.setParent(parentEvent);
		}
		event.setCreatedAt(this.createdAt);
		event.setUpdatedAt(this.updatedAt);
		return event;
	}
	
}
