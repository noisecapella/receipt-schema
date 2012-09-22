package com.schneeloch.receiptreader;

import java.math.BigDecimal;

import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Display;

public class ReceiptItem {
	private final BigDecimal price;
	private final String description;
	private final Paint paint;
	private final int displayWidth;
	private final int displayHeight;
	public ReceiptItem(String description, BigDecimal price, Paint paint, Display display) {
		if (description == null || price == null || paint == null) {
			throw new NullPointerException();
		}
		this.description = description;
		this.price = price;
		this.displayWidth = display.getWidth();
		this.displayHeight = display.getHeight();
		this.paint = paint;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public String getDescription() {
		return description;
	}

	public String makeItemText() {
		StringBuilder ret = new StringBuilder();
		ret.append(description);

		float descriptionWidth = paint.measureText(description);
		float priceWidth = paint.measureText(price.toString());
		float remainderWidth = displayWidth - priceWidth - descriptionWidth;
		if (remainderWidth > 0) {
			float spaceWidth = paint.measureText(" ");
			int numSpaces = (int)(remainderWidth / spaceWidth);
			for (int i = 0; i < numSpaces; i++) {
				ret.append(' ');
			}
		}
		ret.append(price.toString());
		return ret.toString();
	}
}
