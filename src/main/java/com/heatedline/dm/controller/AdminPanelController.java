package com.heatedline.dm.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heatedline.dm.dto.PageContentDTO;
import com.heatedline.dm.exceptions.DuelMastersServiceException;
import com.heatedline.dm.model.CardContent;
import com.heatedline.dm.repository.CardContentRepository;
import com.heatedline.dm.utils.DMConstants;

@RestController
public class AdminPanelController {
	
	@Autowired
	CardContentRepository cardContentRepository;
	
	@GetMapping("/saveSetContents")
	public ResponseEntity<?> saveSetContents(@RequestParam(value = "setName") String setName) throws DuelMastersServiceException {
		URL extractPageContentURL = null;
		HttpURLConnection extractPageContentConn = null;
		PageContentDTO pageContentDTO = null;
		List<CardContent> cardContentList = new ArrayList<CardContent>();
		CardContent cardContent = null;
		StringBuilder response = null;
		int responseCode = 0;
		List<String> cardNameList = new ArrayList<String>();
		try {
			response = new StringBuilder();
			
			String encodedSetName = URLEncoder.encode(setName,"UTF-8");
			
			extractPageContentURL = new URL(DMConstants.PAGE_CONTENT_EXTRACTION_API + encodedSetName + "&prop=wikitext&section=3&format=json");
			extractPageContentConn = (HttpURLConnection) extractPageContentURL.openConnection();
			extractPageContentConn.setReadTimeout(DMConstants.TIMEOUT);
			extractPageContentConn.setConnectTimeout(DMConstants.TIMEOUT);
			extractPageContentConn.setRequestMethod("GET");
			extractPageContentConn.setRequestProperty("Content-Type", "application/json");
			extractPageContentConn.setRequestProperty("Accept", "application/json");
			extractPageContentConn.setDoInput(true);
			extractPageContentConn.setDoOutput(true);
			
			responseCode = extractPageContentConn.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				String line;
				BufferedReader br = new BufferedReader(new InputStreamReader(extractPageContentConn.getInputStream()));
				while ((line = br.readLine()) != null) {
					response.append(line);
				}
			} else {
				response = new StringBuilder();
			}
			
			String contentsPart = response.toString().substring(response.toString().indexOf("== Contents =="), response.toString().length() - 4);
			contentsPart = StringEscapeUtils.unescapeJava(contentsPart);
			String[] contentsArr = contentsPart.split("\n");
			for(String content : contentsArr) {
				if(!content.isEmpty() && content.contains("[[")) {
					String cardName = content.substring(content.indexOf("[[") + 2, content.indexOf("]]"));
					cardNameList.add(cardName);
				}
			}
			
			for(String cardName : cardNameList) {
				response = new StringBuilder();
				String encodedCardName = URLEncoder.encode(cardName,"UTF-8");
				extractPageContentURL = new URL(DMConstants.PAGE_CONTENT_EXTRACTION_API + encodedCardName + "&prop=wikitext&format=json");
				extractPageContentConn = (HttpURLConnection) extractPageContentURL.openConnection();
				extractPageContentConn.setReadTimeout(DMConstants.TIMEOUT);
				extractPageContentConn.setConnectTimeout(DMConstants.TIMEOUT);
				extractPageContentConn.setRequestMethod("GET");
				extractPageContentConn.setRequestProperty("Content-Type", "application/json");
				extractPageContentConn.setRequestProperty("Accept", "application/json");
				extractPageContentConn.setDoInput(true);
				extractPageContentConn.setDoOutput(true);
				
				responseCode = extractPageContentConn.getResponseCode();

				if (responseCode == HttpURLConnection.HTTP_OK) {
					String line;
					BufferedReader br = new BufferedReader(new InputStreamReader(extractPageContentConn.getInputStream()));
					while ((line = br.readLine()) != null) {
						response.append(line);
					}
				} else {
					response = new StringBuilder();
				}
				
				String properJson = response.toString().replace("\"*\"", "\"details\"");
				pageContentDTO = new ObjectMapper().readValue(properJson, PageContentDTO.class);
				
				cardContent = new CardContent();
				cardContent.setTitle(pageContentDTO.getParse().getTitle());
				cardContent.setDetails(pageContentDTO.getParse().getWikitext().getDetails());
				
				cardContent = cardContentRepository.save(cardContent);
				cardContentList.add(cardContent);
				System.out.println(cardContent.getTitle() + " is uploaded.");
				break;
			}
			
			return new ResponseEntity<List<CardContent>>(cardContentList, HttpStatus.OK);
		} catch (RuntimeException | IOException e) {
			throw new DuelMastersServiceException("CardContentController", "saveSetContents", "Failed to save set contents", e);
		}
	}

}
