package com.heatedline.dm.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heatedline.dm.model.CardContent;
import com.heatedline.dm.repository.CardContentRepository;

@Service("CardContentService")
@Transactional
public class CardContentServiceImpl implements CardContentService {

	@Autowired
	CardContentRepository cardContentRepository;
	
	
	@Override
	public CardContent saveCardContent(CardContent cardContent) throws RuntimeException {
		// TODO Auto-generated method stub
		try {
			cardContent = cardContentRepository.save(cardContent);
			return cardContent;
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}


	@Override
	public String deleteCardContent(String id) throws RuntimeException {
		// TODO Auto-generated method stub
		Optional<CardContent> cardContent = cardContentRepository.findById(id);
		if(cardContent != null) {
			cardContentRepository.delete(cardContent.get());
		}
		return "Deleted";
	}


	@Override
	public List<CardContent> getAllCardContents() throws RuntimeException {
		// TODO Auto-generated method stub
		
		return null;
	}

	

	

}
