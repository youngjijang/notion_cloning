package com.example.notion_cloning;

import java.time.LocalDateTime;

public record SaveResponse(
	Long id,
	String title,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {

}
