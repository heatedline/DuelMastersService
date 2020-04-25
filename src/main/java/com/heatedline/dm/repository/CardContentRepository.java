package com.heatedline.dm.repository;

import org.springframework.data.solr.repository.SolrCrudRepository;

import com.heatedline.dm.model.CardContent;

public interface CardContentRepository extends SolrCrudRepository<CardContent, Long> {

	CardContent findByCardId(Long cardId);
	
}
