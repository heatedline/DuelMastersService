package com.heatedline.dm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.heatedline.dm.model.CardImageFile;

@RepositoryRestResource(path = "files", collectionResourceRel = "files")
public interface CardImageFileRepository extends JpaRepository<CardImageFile, Long> {
	
	CardImageFile findByContentId(String contentId);

}
