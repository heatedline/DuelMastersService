package com.heatedline.dm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection = "DuelMasters")
public class CardContent {

	@Id
	@Indexed(name = "cardId", type = "long")
	private Long cardId;

	@Indexed(name = "pageid", type = "long")
	private Long pageid;

	@Indexed(name = "ns", type = "long")
	private Long ns;

	@Indexed(name = "title", type = "string")
	private String title;

	@Indexed(name = "content", type = "string")
	private String revisions;

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public Long getPageid() {
		return pageid;
	}

	public void setPageid(Long pageid) {
		this.pageid = pageid;
	}

	public Long getNs() {
		return ns;
	}

	public void setNs(Long ns) {
		this.ns = ns;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRevisions() {
		return revisions;
	}

	public void setRevisions(String revisions) {
		this.revisions = revisions;
	}

}
