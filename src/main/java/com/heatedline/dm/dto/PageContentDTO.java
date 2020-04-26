package com.heatedline.dm.dto;

public class PageContentDTO {

	private Parse parse;

	public Parse getParse() {
		return parse;
	}

	public void setParse(Parse parse) {
		this.parse = parse;
	}

	@Override
	public String toString() {
		return "PageContentDTO [parse=" + parse + "]";
	}

}
