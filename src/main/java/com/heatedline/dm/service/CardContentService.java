package com.heatedline.dm.service;

import java.util.List;

import com.heatedline.dm.model.CardContent;

public interface CardContentService {
	
	public CardContent saveCardContent(CardContent cardContent) throws RuntimeException;
	public String deleteCardContent(String id) throws RuntimeException;
	public List<CardContent> getAllCardContents() throws RuntimeException;

}
