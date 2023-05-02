package com.heatedline.dm.contentStore;

import org.springframework.content.commons.renditions.Renderable;
import org.springframework.content.commons.repository.ContentStore;
import org.springframework.content.commons.search.Searchable;

import com.heatedline.dm.model.CardImageFile;

public interface CardImageFileContentStore extends ContentStore<CardImageFile, String>, Searchable<String>, Renderable<CardImageFile> {

}
