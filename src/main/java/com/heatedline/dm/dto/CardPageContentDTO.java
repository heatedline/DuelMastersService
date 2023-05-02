package com.heatedline.dm.dto;

import java.io.File;

public class CardPageContentDTO {

	private Long pageId;
	private String pageTitle;
	private File imageFile;
	private String pageDesc;

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public File getImageFile() {
		return imageFile;
	}

	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	public String getPageDesc() {
		return pageDesc;
	}

	public void setPageDesc(String pageDesc) {
		this.pageDesc = pageDesc;
	}

	@Override
	public String toString() {
		return "CardPageContentDTO [pageId=" + pageId + ", pageTitle=" + pageTitle + ", imageFile=" + imageFile
				+ ", pageDesc=" + pageDesc + "]";
	}

}
