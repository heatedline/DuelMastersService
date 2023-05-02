package com.heatedline.dm.dto;

import java.util.List;

public class CardSuggestionsDTO {

	private List<CardTitle> items;

	public List<CardTitle> getItems() {
		return items;
	}

	public void setItems(List<CardTitle> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CardSuggestionsDTO [items=").append(items).append("]");
		return builder.toString();
	}

}
