package com.example.notion_cloning;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@Transactional
class DocumentServiceTest {

	@Autowired
	private DocumentService documentService;

	@Autowired
	private DocumentRepository documentRepository;

	private Document oneDocs;

	@BeforeEach
	void setUp(){

		// document save
		oneDocs = documentRepository.save(new Document("root","내용1", null));
	}

	@Test
	void test(){

		// save
		var childId = documentService.save(new SaveRequest("자식1",oneDocs.getId())).id();
		documentService.save(new SaveRequest("root2",null));
		documentService.save(new SaveRequest("자식2",oneDocs.getId()));
		var chchcar = documentService.save(new SaveRequest("자식1자식1",childId));

		// findAll
		var res = documentService.findAllTree();
		System.out.println(res.size());

		// find
		var res2 = documentService.findById(oneDocs.getId());
		System.out.println(res2);

		// update
		documentService.update(new UpdateRequest("수정 자식1", "내용 생겼어요"), childId );
		var res3 = documentService.findById(childId);
		System.out.println(res);

		// delete
		documentService.delete(childId);
		documentRepository.flush();
		var findAll = documentService.findAllTree();
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println(findAll.size());


	}

}