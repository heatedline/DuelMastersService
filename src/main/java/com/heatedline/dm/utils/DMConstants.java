package com.heatedline.dm.utils;

/**
 * Class to store static constant data needed for our application.
 * @author heatedline
 *
 */
public class DMConstants {
	
	public static final int TIMEOUT = 55000;
	
	public static final String SETS_PAGE_CONTENT_EXTRACTION_API = "https://duelmasters.fandom.com/api.php?action=parse&page=";
	public static final String CARDS_PAGE_CONTENT_EXTRACTION_API = "https://duelmasters.fandom.com/api.php?action=query&prop=revisions&rvslots=%2A&rvprop=content&format=xml&titles=";
	public static final String CARDS_IMAGE_EXTRACTION_API = "https://duelmasters.fandom.com/api.php?action=parse&prop=text&format=xml&pageid=";
	
	public static final String SUGGESTIONS_API = "https://duelmasters.fandom.com/api/v1/SearchSuggestions/List?query=";

	
	public static final String FUNCTION_UPLOAD_CARD_DETAILS = "Upload Card Details";
	public static final String FUNCTION_UPLOAD_CARD_IMAGE = "Upload Card Image";
	
	/*************************Keywords****************************/
	public static final String KEYWORD_CIVILIZATION = "civilization";
	public static final String KEYWORD_TYPE = "type";
	public static final String KEYWORD_OCGNAME = "ocgname";
	public static final String KEYWORD_COST = "cost";
	public static final String KEYWORD_EFFECT = "effect";
	public static final String KEYWORD_OCGEFFECT = "ocgeffect";
	public static final String KEYWORD_FLAVOR = "flavor";
	public static final String KEYWORD_MANA = "mana";
	public static final String KEYWORD_ARTIST = "artist";
	public static final String KEYWORD_SET = "set";
	
}
