package com.heatedline.dm.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.heatedline.dm.dto.CardPageContentDTO;

/**
 * @author Supratim
 *
 */
public class CardUtil {

	public static List<CardPageContentDTO> getCardDetailsObjFromXML(String xmlContent) {
		String s = StringEscapeUtils.unescapeJava(xmlContent);
		s = s.replaceAll("& ", "&amp; ");
		List<CardPageContentDTO> cardPageContentDTOList = null;
		try {
			stringToDom(s);
			cardPageContentDTOList = parseCardDetailsXML();
		} catch (IOException | SAXException | ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cardPageContentDTOList;
	}
	
	public static CardPageContentDTO getCardImageDetailsObjFromXml(String xmlContent, CardPageContentDTO cardPageContentDTO) {
		String s = StringEscapeUtils.unescapeJava(xmlContent);
		s = s.replaceAll("& ", "&amp; ");
		try {
			stringToDom(s);
			cardPageContentDTO = parseCardImageXML(cardPageContentDTO);
		} catch (IOException | SAXException | ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cardPageContentDTO;
	}
	
	/**
	 * Method which parses the XML content of card page to fill in related Java Object
	 * 
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private static List<CardPageContentDTO> parseCardDetailsXML() throws ParserConfigurationException, SAXException, IOException {
		CardPageContentDTO cardPageContentDTO = null;
		List<CardPageContentDTO> cardPageContentDTOList = new ArrayList<CardPageContentDTO>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(ResourceUtils.getFile("classpath:myFile.xml"));
		document.getDocumentElement().normalize();

		NodeList nListPages = document.getElementsByTagName("pages");
		Node node = nListPages.item(0);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			NodeList nListPage = node.getChildNodes();
			for (int temp = 0; temp < nListPage.getLength(); temp++) {
				Node childNode = nListPage.item(temp);
				if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) childNode;
					if (eElement.getNodeName().equals("page")) {
						cardPageContentDTO = new CardPageContentDTO();
						cardPageContentDTO.setPageId(Long.parseLong(eElement.getAttributes().getNamedItem("pageid").getNodeValue()));
						cardPageContentDTO.setPageTitle(eElement.getAttributes().getNamedItem("title").getNodeValue());
						for(int t = 0; t < childNode.getChildNodes().getLength(); t++) {
							if(childNode.getChildNodes().item(t).getNodeType() == Node.ELEMENT_NODE) {
								Element revElement = (Element) childNode.getChildNodes().item(t);
								cardPageContentDTO.setPageDesc(revElement.getElementsByTagName("rev").item(0).getTextContent());
								System.out.println(revElement.getElementsByTagName("rev").item(0).getTextContent());
								String[] pageDescArr = revElement.getElementsByTagName("rev").item(0).getTextContent().split("|");
								for(String s : pageDescArr) {
									//System.out.println(s);
								}
							}
						}
						cardPageContentDTOList.add(cardPageContentDTO);
					}
				}
			}
		}
		return cardPageContentDTOList;
	}
	
	private static CardPageContentDTO parseCardImageXML(CardPageContentDTO cardPageContentDTO) throws ParserConfigurationException, SAXException, IOException {
		URL imageURL = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(ResourceUtils.getFile("classpath:myFile.xml"));
		document.getDocumentElement().normalize();
		
		NodeList nListParse = document.getElementsByTagName("parse");
		Node node = nListParse.item(0);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			NodeList nListText = node.getChildNodes();
			for (int temp = 0; temp < nListText.getLength(); temp++) {
				Node childNode = nListText.item(temp);
				if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) childNode;
					String s = eElement.getTextContent().substring(eElement.getTextContent().indexOf("https://vignette.wikia.nocookie.net/duelmasters/"), eElement.getTextContent().indexOf("class=\"image image-thumbnail\""));
					String properImageLink = s.trim().substring(0, s.trim().length() - 1);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					InputStream is = null;
					try {
						imageURL = new URL(properImageLink);
						is = imageURL.openStream();
						byte[] byteChunk = new byte[4096];
						int n;
						while ((n = is.read(byteChunk)) > 0) {
							baos.write(byteChunk, 0, n);
						}
					} catch (IOException e) {
						System.err.printf("Failed while reading bytes from %s: %s", imageURL.toExternalForm(), e.getMessage());
						e.printStackTrace();
					} finally {
						if (is != null) {
							is.close();
						}
					}
					File imageFile = ResourceUtils.getFile("image.jpg");
					if(imageFile.exists()) {
						imageFile.delete();
						imageFile.createNewFile();
						FileUtils.writeByteArrayToFile(imageFile, baos.toByteArray());
					} else {
						imageFile.createNewFile();
						FileUtils.writeByteArrayToFile(imageFile, baos.toByteArray());
					}
					
					cardPageContentDTO.setImageFile(imageFile);
					
				}
			}
		}
		return cardPageContentDTO;
	}

	/**
	 * Method to write XML string content to a proper XML file.
	 *
	 * @param xmlSource the XML string content source
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public static void stringToDom(String xmlSource)
			throws SAXException, ParserConfigurationException, IOException, TransformerException {
		// Parse the given input
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xmlSource)));

		// Write the parsed document to an XML file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);

		File xmlFile = ResourceUtils.getFile("classpath:myFile.xml");
		if (xmlFile.exists()) {
			xmlFile.delete();
			xmlFile.createNewFile();
			StreamResult result = new StreamResult(xmlFile);
			transformer.transform(source, result);
		} else {
			StreamResult result = new StreamResult(xmlFile);
			transformer.transform(source, result);
		}
	}

}
