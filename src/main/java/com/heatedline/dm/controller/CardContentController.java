package com.heatedline.dm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.heatedline.dm.exceptions.DuelMastersServiceException;
import com.heatedline.dm.model.CardContent;
import com.heatedline.dm.service.CardContentService;

@RestController
public class CardContentController {

	@Autowired
	CardContentService cardContentService;
	
	@PostMapping("/saveCardContent")
	public ResponseEntity<?> saveCardContent(@RequestBody CardContent cardContent) throws DuelMastersServiceException {
		try {
			cardContent = cardContentService.saveCardContent(cardContent);
			return new ResponseEntity<CardContent>(cardContent, HttpStatus.OK);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			throw new DuelMastersServiceException("CardContentController", "saveCardContent", "Failed to store card content in Apache Solr server.", e);
		}
	}
	
	/*@GetMapping("/getAllCards")
	public ResponseEntity<?> getAllCards() throws DuelMastersServiceException {
		
	}*/
	
	@GetMapping("/deleteCardContent/{id}")
	public ResponseEntity<?> deleteCardContent(@PathVariable String id) throws DuelMastersServiceException {
		try {
			String response = cardContentService.deleteCardContent(id);
			return new ResponseEntity<String>(response, HttpStatus.OK);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			throw new DuelMastersServiceException("CardContentController", "saveCardContent", "Failed to store card content in Apache Solr server.", e);
		}
	}

}
