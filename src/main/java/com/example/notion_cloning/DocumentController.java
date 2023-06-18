package com.example.notion_cloning;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/documents")
public class DocumentController {

	private final DocumentService documentService;

	@GetMapping()
	public ResponseEntity<List<DocumentsResponse>> findAll() {
		return ResponseEntity.ok().body(documentService.findAllTree());
	}

	@GetMapping("/{id}")
	public ResponseEntity<DocumentResponse> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(documentService.findById(id));
	}

	@PostMapping()
	public ResponseEntity<SaveResponse> save(@RequestBody SaveRequest request) {
		return ResponseEntity.ok().body(documentService.save(request));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody UpdateRequest request, @PathVariable Long id) {
		documentService.update(request, id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		documentService.delete(id);
		return ResponseEntity.ok().build();
	}
}
