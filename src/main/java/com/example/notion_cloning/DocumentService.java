package com.example.notion_cloning;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.notion_cloning.DocumentResponse.ChildDocuments;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentService {

	private final DocumentRepository documentRepository;

	@Transactional(readOnly = true)
	public List<DocumentsResponse> findAllTree() {
		documentRepository.findAll();
		List<Document> documents = documentRepository.findAllByParentIsNull();
		List<DocumentsResponse> res = new ArrayList<>();
		for (Document d : documents) {
			res.add(convertToDocumentsDto(d));
		}
		return res;
	}

	@Transactional(readOnly = true)
	public DocumentResponse findById(Long id) {
		Document document = documentRepository.findByIdWithChild(id)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 id"));
		return convertToDocumentDto(document);
	}

	public SaveResponse save(SaveRequest request) {
		Document document = new Document();
		if (request.parent() != null) {
			Document parents = documentRepository.findById(request.parent())
				.orElseThrow(() -> new RuntimeException("존재하지 않는 parents id"));
			parents.addDocument(document);
			// 부모꺼에 자식 추가?
		}
		document.setTitle(request.title());

		document = documentRepository.save(document);

		return new SaveResponse(document.getId(), document.getTitle(), document.getCreatedAt(),
			document.getCreatedAt());
	}

	public void update(UpdateRequest request, Long id) {
		Document document = documentRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 id"));
		document.setTitle(request.title());
		document.setContent(request.content());
		documentRepository.save(document);
	}

	public void delete(Long id) {
		Document document = documentRepository.findByIdWithChild(id)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 id"));
		// 하위 데이터 parent를 root document로 변경 -> todo 최상단에 뭐가 ㅇ있어도 무조건 root???
		if (!document.getDocuments().isEmpty()) {
			List<Document> child = document.getDocuments();
			for (Document d : child) {
				d.setParent(null);
			}
		}
		documentRepository.delete(document);
	}

	private DocumentsResponse convertToDocumentsDto(Document document) {
		List<DocumentsResponse> childDocuments = document.getDocuments().stream()
			.map(this::convertToDocumentsDto)
			.collect(Collectors.toList());

		return new DocumentsResponse(document.getId(), document.getTitle(), childDocuments);
	}

	private DocumentResponse convertToDocumentDto(Document document) {
		List<ChildDocuments> childDocuments = document.getDocuments()
			.stream()
			.map(ChildDocuments::new)
			.collect(Collectors.toList());

		return new DocumentResponse(document.getId(), document.getTitle(), document.getContent(), childDocuments,
			document.getCreatedAt(), document.getUpdatedAt());
	}
}
