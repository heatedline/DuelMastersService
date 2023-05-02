package com.heatedline.dm.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;
import org.springframework.content.commons.annotations.MimeType;

@Entity
@Table(name = "cardImageFile")
@NamedQuery(name = "CardImageFile.findAll", query = "SELECT f FROM CardImageFile f")
public class CardImageFile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long pageId;

	private String title;
	private Date created = new Date();

	@ContentId
	private String contentId;

	@ContentLength
	private long contentLength;

	@MimeType
	private String mimeType = "text/plain";

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	@Override
	public String toString() {
		return "CardImageFile [pageId=" + pageId + ", title=" + title + ", created=" + created + ", contentId="
				+ contentId + ", contentLength=" + contentLength + ", mimeType=" + mimeType + "]";
	}

}
