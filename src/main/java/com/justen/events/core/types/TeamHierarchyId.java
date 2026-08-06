package com.justen.events.core.types;

import java.io.Serializable;
import java.util.UUID;

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
@EqualsAndHashCode
public class TeamHierarchyId implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID userId;

	private UUID teamId;

}