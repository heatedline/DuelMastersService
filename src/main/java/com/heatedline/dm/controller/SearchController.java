package com.heatedline.dm.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.heatedline.dm.dto.CardSuggestionsDTO;
import com.heatedline.dm.dto.CardTitle;
import com.heatedline.dm.exceptions.DuelMastersServiceException;
import com.heatedline.dm.model.CardContent;
import com.heatedline.dm.repository.CardContentRepository;
import com.heatedline.dm.utils.DMConstants;

@RestController
public class SearchController {
	
	@Autowired
	CardContentRepository cardContentRepository;
	
	@GetMapping("searchSuggestions")
	public ResponseEntity<List<String>> searchSuggestions(@RequestParam(value = "searchTerm") String searchTerm) throws DuelMastersServiceException {
		URL suggestionsURL = null;
		HttpURLConnection suggestionsURLConn = null;
		StringBuilder response = null;
		int responseCode = 0;
		try {
			response = new StringBuilder();
			String encodedSearchTerm = URLEncoder.encode(searchTerm,"UTF-8");
			suggestionsURL = new URL(DMConstants.SUGGESTIONS_API + encodedSearchTerm);
			suggestionsURLConn = (HttpURLConnection) suggestionsURL.openConnection();
			suggestionsURLConn.setReadTimeout(DMConstants.TIMEOUT);
			suggestionsURLConn.setConnectTimeout(DMConstants.TIMEOUT);
			suggestionsURLConn.setRequestMethod("GET");
			suggestionsURLConn.setRequestProperty("Content-Type", "application/json");
			suggestionsURLConn.setRequestProperty("Accept", "application/json");
			suggestionsURLConn.setDoInput(true);
			suggestionsURLConn.setDoOutput(true);
			
			responseCode = suggestionsURLConn.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				String line;
				BufferedReader br = new BufferedReader(new InputStreamReader(suggestionsURLConn.getInputStream()));
				while ((line = br.readLine()) != null) {
					response.append(line);
				}
			} else {
				response = new StringBuilder();
			}
			
			System.out.println(response);
			
			Gson gson = new Gson(); // Or use new GsonBuilder().create();
			CardSuggestionsDTO cardSuggestions = gson.fromJson(response.toString(), CardSuggestionsDTO.class);
			
			List<String> cardTitles = cardSuggestions.getItems().stream().map(CardTitle::getTitle).collect(Collectors.toList());
			
			return new ResponseEntity<List<String>>(cardTitles, HttpStatus.OK);
			
		} catch (Exception e) {
			throw new DuelMastersServiceException("SearchController", "searchSuggestions", "Failed to show suggestions for the search term", e);
		}
	}
	
	@GetMapping("search")
	public ResponseEntity<?> search(@RequestParam(value = "searchTerm") String searchTerm) throws DuelMastersServiceException {
		try {
			List<Optional<CardContent>> objList = cardContentRepository.findByDetailsContaining(searchTerm);
			List<CardContent> cardContentList = new ArrayList<CardContent>();
			for(Optional<CardContent> cardContent : objList) {
				cardContent = cardContentRepository.findById(cardContent.get().getId());
				cardContentList.add(cardContent.get());
			}
			return new ResponseEntity<List<CardContent>>(cardContentList, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new DuelMastersServiceException("SearchController", "search", "Failed to search for the card", e);
		}
	}

}
