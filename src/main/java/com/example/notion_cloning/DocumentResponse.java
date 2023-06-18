package com.example.notion_cloning;

import java.time.LocalDateTime;
import java.util.List;

public record DocumentResponse(
	Long id,
	String title,
	String contents,
	List<ChildDocuments> documents,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
}
