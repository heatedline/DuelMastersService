package com.heatedline.dm.dto;

public class CardTitle {

	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CardTitle [title=").append(title).append("]");
		return builder.toString();
	}

}
