package com.example.notion_cloning;

public record SaveRequest(
	String title,
	Long parent
) {
}
