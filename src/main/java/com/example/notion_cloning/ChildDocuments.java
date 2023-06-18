package com.example.notion_cloning;

import java.time.LocalDateTime;

record ChildDocuments (
	Long id,
	String title,
	LocalDateTime createdAt,
	LocalDateTime updateAt
) {
}