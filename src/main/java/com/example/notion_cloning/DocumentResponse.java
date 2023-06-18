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

	static class ChildDocuments {
		private Long id;
		private String title;
		private LocalDateTime createdAt;
		private LocalDateTime updateAt;

		public ChildDocuments(Document document) {
			id = document.getId();
			title = document.getTitle();
			createdAt = document.getCreatedAt();
			updateAt = document.getUpdatedAt();
		}

	}
}
