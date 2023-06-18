package com.example.notion_cloning;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocumentRepository extends JpaRepository<Document, Long> {

	@Query("select distinct d from Document d left join fetch d.documents where d.id = :id")
	Optional<Document> findByIdWithChild(Long id);

	List<Document> findAllByParentIsNull();
}
