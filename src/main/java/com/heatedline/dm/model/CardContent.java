package com.heatedline.dm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * Model class to map with SolrDocument and store necessary information to
 * ApacheSolr server.
 * 
 * @author heatedline
 *
 */
@SolrDocument(collection = "cardcontent")
public class CardContent {

	@Id
	@Indexed(name = "id", type = "string")
	private String id;

	@Indexed(name = "pageId", type = "long")
	private Long pageId;

	@Indexed(name = "title", type = "string")
	private String title;

	@Indexed(name = "details", type = "string")
	private String details;

	@Indexed(name = "_version_", type = "long")
	private Long _version_;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long get_version_() {
		return _version_;
	}

	public void set_version_(Long _version_) {
		this._version_ = _version_;
	}

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

}
