package com.schneeloch.receiptreader;

import java.math.BigDecimal;

public class ReceiptItem {
	private final BigDecimal price;
	private final String description;
	public ReceiptItem(String description, BigDecimal price) {
		this.description = description;
		this.price = price;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public String getDescription() {
		return description;
	}
}
