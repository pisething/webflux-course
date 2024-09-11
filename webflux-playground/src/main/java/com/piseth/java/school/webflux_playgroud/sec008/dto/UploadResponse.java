package com.piseth.java.school.webflux_playgroud.sec008.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadResponse {
	private UUID confirmationId;
	private Long productCount;
}
