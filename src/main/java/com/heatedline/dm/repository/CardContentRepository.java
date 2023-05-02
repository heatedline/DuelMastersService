package com.heatedline.dm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.solr.repository.SolrCrudRepository;

import com.heatedline.dm.model.CardContent;

/**
 * SolrCrudRepository for Spring Data Solr operations.
 * @author heatedline
 *
 */
public interface CardContentRepository extends SolrCrudRepository<CardContent, Long> {

	Optional<CardContent> findById(String id);
	
	List<Optional<CardContent>> findByDetailsContaining(String searchTerm);
	
	
}
