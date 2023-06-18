package com.example.notion_cloning;

import java.util.List;

public record DocumentsResponse(
	Long id,
	String title,
	List<DocumentsResponse> documents
) {
}
