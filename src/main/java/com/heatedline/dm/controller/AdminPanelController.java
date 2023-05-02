package com.heatedline.dm.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.heatedline.dm.contentStore.CardImageFileContentStore;
import com.heatedline.dm.dto.CardPageContentDTO;
import com.heatedline.dm.model.CardContent;
import com.heatedline.dm.model.CardImageFile;
import com.heatedline.dm.repository.CardContentRepository;
import com.heatedline.dm.repository.CardImageFileRepository;
import com.heatedline.dm.utils.DMConstants;
import com.heatedline.dm.utils.CardUtil;

@RestController
public class AdminPanelController {
	
	@Autowired
	CardContentRepository cardContentRepository;
	@Autowired
	CardImageFileRepository cardImageFileRepository;
	@Autowired
	CardImageFileContentStore cardImageFileContentStore;
	
	@GetMapping("/saveSetContents")
	public ResponseEntity<?> saveSetContents(@RequestParam(value = "setName") String setName) {
		URL extractPageContentURL = null;
		HttpURLConnection extractPageContentConn = null;
		URL extractCardImageURL = null;
		HttpURLConnection extractCardImageConn = null;
		List<CardContent> cardContentList = null;
		List<List<CardContent>> cardContentMasterList = new ArrayList<List<CardContent>>();
		List<CardPageContentDTO> cardPageContentDTOList = null;
		List<CardPageContentDTO> ouputList = null;
		CardContent cardContent = null;
		StringBuilder response = null;
		int responseCode = 0;
		List<String> cardNameList = new ArrayList<String>();
		try {
			response = new StringBuilder();
			
			String encodedSetName = URLEncoder.encode(setName,"UTF-8");
			
			extractPageContentURL = new URL(DMConstants.SETS_PAGE_CONTENT_EXTRACTION_API + encodedSetName + "&prop=wikitext&section=3&format=json");
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
			
			List<List<String>> partitionedList = ListUtils.partition(cardNameList, 50);
			for(List<String> partition : partitionedList) {
				response = new StringBuilder();
				String titles = "";
				for(String s : partition) {
					String encodedCardName = URLEncoder.encode(s,"UTF-8");
					titles = titles + encodedCardName + "|";
					break;
				}
				titles = titles.substring(0, titles.length() - 1);
				extractPageContentURL = new URL(DMConstants.CARDS_PAGE_CONTENT_EXTRACTION_API + titles);
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
				
				cardPageContentDTOList = CardUtil.getCardDetailsObjFromXML(response.toString());
				ouputList = new ArrayList<CardPageContentDTO>();
				for(CardPageContentDTO cardPageContentDTO : cardPageContentDTOList) {
					response = new StringBuilder();
					extractCardImageURL = new URL(DMConstants.CARDS_IMAGE_EXTRACTION_API + cardPageContentDTO.getPageId());
					extractCardImageConn = (HttpURLConnection) extractCardImageURL.openConnection();
					extractCardImageConn.setReadTimeout(DMConstants.TIMEOUT);
					extractCardImageConn.setConnectTimeout(DMConstants.TIMEOUT);
					extractCardImageConn.setRequestMethod("GET");
					extractCardImageConn.setRequestProperty("Content-Type", "application/json");
					extractCardImageConn.setRequestProperty("Accept", "application/json");
					extractCardImageConn.setDoInput(true);
					extractCardImageConn.setDoOutput(true);
					
					responseCode = extractCardImageConn.getResponseCode();

					if (responseCode == HttpURLConnection.HTTP_OK) {
						String line;
						BufferedReader br = new BufferedReader(new InputStreamReader(extractCardImageConn.getInputStream()));
						while ((line = br.readLine()) != null) {
							response.append(line);
						}
					} else {
						response = new StringBuilder();
					}
					
					cardPageContentDTO = CardUtil.getCardImageDetailsObjFromXml(response.toString(), cardPageContentDTO);
					ouputList.add(cardPageContentDTO);
					break;
				}
				
				cardContentList = new ArrayList<CardContent>();
				for(CardPageContentDTO cardPageContentDTO : ouputList) {
					cardContent = new CardContent();
					cardContent.setPageId(cardPageContentDTO.getPageId());
					cardContent.setTitle(cardPageContentDTO.getPageTitle());
					cardContent.setDetails(cardPageContentDTO.getPageDesc());
					
					CardImageFile cardImageFile = new CardImageFile();
					cardImageFile.setPageId(cardPageContentDTO.getPageId());
					cardImageFile.setTitle(cardPageContentDTO.getPageTitle());
					cardImageFile.setMimeType("image/jpeg");
					cardImageFile.setContentLength(cardPageContentDTO.getImageFile().length());
					cardImageFile = cardImageFileRepository.save(cardImageFile);
					cardImageFileContentStore.setContent(cardImageFile, FileUtils.openInputStream(cardPageContentDTO.getImageFile()));
					cardImageFileRepository.save(cardImageFile);
					System.out.println(cardImageFile.toString());
					cardContent = cardContentRepository.save(cardContent);
					cardContentList.add(cardContent);
					System.out.println(cardContent.getTitle() + " is added successfully.");
				}
				cardContentMasterList.add(cardContentList);
				break;
			}
			
		} catch (RuntimeException | IOException e) {
			//throw new DuelMastersServiceException("CardContentController", "saveSetContents", "Failed to save set contents", e);
			e.printStackTrace();
		}
		
		return new ResponseEntity<List<List<CardContent>>>(cardContentMasterList, HttpStatus.OK);
	}

}
