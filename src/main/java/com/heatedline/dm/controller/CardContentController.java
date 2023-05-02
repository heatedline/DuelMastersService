package com.heatedline.dm.controller;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.heatedline.dm.contentStore.CardImageFileContentStore;
import com.heatedline.dm.exceptions.DuelMastersServiceException;
import com.heatedline.dm.model.CardContent;
import com.heatedline.dm.model.CardImageFile;
import com.heatedline.dm.repository.CardImageFileRepository;
import com.heatedline.dm.service.CardContentService;

@RestController
public class CardContentController {

	@Autowired
	CardContentService cardContentService;
	@Autowired
	CardImageFileRepository cardImageFileRepository;
	@Autowired
	CardImageFileContentStore cardImageFileContentStore;
	
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
	
	@RequestMapping(value = "/cardImage/{fileId}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<?> getCardImage(@PathVariable("fileId") Long id, @RequestHeader HttpHeaders headers,  HttpServletResponse response) {
		try {
			Optional<CardImageFile> f = cardImageFileRepository.findById(id);
			if (f.isPresent()) {
				byte[] content = IOUtils.toByteArray(cardImageFileContentStore.getContent(f.get()));
				ByteArrayInputStream bis = new ByteArrayInputStream(content);
				
				IOUtils.copy(bis, response.getOutputStream());

			    response.setContentType(f.get().getMimeType());
			    response.setHeader("Content-disposition", " filename=" + f.get().getTitle());

			    response.flushBuffer();
			    return new ResponseEntity<String>("getCardImage Successful", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("File not found", HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
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
	
	@GetMapping("deleteCardImage")
	public ResponseEntity<?> deleteCardImage(@RequestParam(value = "fileId") Long fileId) {
		try {
			String status = "";
			Optional<CardImageFile> f = cardImageFileRepository.findById(fileId);
			if (f.isPresent()) {
				cardImageFileRepository.delete(f.get());
				cardImageFileContentStore.unsetContent(f.get());
				status = "deleted";
			} else {
				status = "File Not Found";
			}
			return new ResponseEntity<String>(status, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
