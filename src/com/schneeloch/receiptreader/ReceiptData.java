package com.schneeloch.receiptreader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Paint;
import android.net.Uri;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Display;

public class ReceiptData {
	// these fields may be null if not specified in URL
	
	private final BigDecimal tax;
	private final String units;
	private final BigDecimal paidTotal;
	private final String establishmentName;
	private final String establishmentDescription;
	private final Long transactionDate;
	private final String orderNumber;
	private final String address;
	private final String xtra;
	
	private final ArrayList<ReceiptItem> items = new ArrayList<ReceiptItem>();
	
	private final static String TAX_KEY = "t";
	private final static String UNIT_KEY = "u";
	private final static String PAID_TOTAL_KEY = "pt";
	private final static String ESTABLISHMENT_NAME_KEY = "en";
	private final static String ESTABLISHMENT_DESCRIPTION_KEY = "ed";
	private final static String DATE_KEY = "d";
	private final static String ORDER_KEY = "o";
	private final static String ADDRESS_KEY = "a";
	private final static String XTRA_KEY = "x";
	
	private final HashMap<String, String> textRepresentation = 
			new HashMap<String, String>();
			
	
	private final static String formattingLayout = "%en\n%ed\n*%i\n*%o\n%a\n%x\n";
	
	public ReceiptData(Uri uri, Paint paint, Display display) {
		tax = parseBigDecimal(uri, TAX_KEY, null);
		textRepresentation.put(TAX_KEY, tax != null ? "Tax: " + tax : "");

		units = parseString(uri, UNIT_KEY, "");
		textRepresentation.put(UNIT_KEY, units);
		
		paidTotal = parseBigDecimal(uri, PAID_TOTAL_KEY, null);
		textRepresentation.put(PAID_TOTAL_KEY, paidTotal != null ? "Total: " + paidTotal : "");
		
		establishmentName = parseString(uri, ESTABLISHMENT_NAME_KEY, "");
		textRepresentation.put(ESTABLISHMENT_NAME_KEY, establishmentName);

		establishmentDescription = parseString(uri, ESTABLISHMENT_DESCRIPTION_KEY, "");
		textRepresentation.put(ESTABLISHMENT_DESCRIPTION_KEY, establishmentDescription);
		
		transactionDate = parseLong(uri, DATE_KEY, null);
		textRepresentation.put(DATE_KEY, transactionDate != null ? "Date: " + transactionDate : "");
		
		orderNumber = parseString(uri, ORDER_KEY, "");
		textRepresentation.put(ORDER_KEY, (orderNumber != null && orderNumber.length() != 0) ? "Order number: " + orderNumber : "");
		
		address = parseString(uri, ADDRESS_KEY, "");
		textRepresentation.put(ADDRESS_KEY, address);
		
		xtra = parseString(uri, XTRA_KEY, "");
		textRepresentation.put(XTRA_KEY, xtra);
		
		int i = 1;
		String itemDescription = parseString(uri, itemDescriptionKey(i), "");
		while (itemDescription != null && itemDescription.length() > 0) {
			BigDecimal itemPrice = parseBigDecimal(uri, itemPriceKey(i), null);
			ReceiptItem item = new ReceiptItem(itemDescription, itemPrice, 
					paint, display);
			items.add(item);
			
			i += 1;
			itemDescription = parseString(uri, itemDescriptionKey(i), "");
		}
	}

	private String itemDescriptionKey(int i) {
		if (i < 1) {
			throw new RuntimeException("item key out of range");
		}
		return "i" + i + "d";
	}

	private String itemPriceKey(int i) {
		if (i < 1) {
			throw new RuntimeException("item key out of range");
		}
		return "i" + i + "p";
	}

	private Long parseLong(Uri uri, String key, Long defaultValue) {
		String value = uri.getQueryParameter(key);
		if (value == null) {
			return defaultValue;
		}
		else
		{
			try
			{
				return Long.parseLong(value);
			}
			catch (NumberFormatException e) {
				Log.e(Constants.LOG_TAG, e.toString());
				return defaultValue;
			}
		}
	}

	private BigDecimal parseBigDecimal(Uri uri, String key,
			BigDecimal defaultValue) {
		String value = uri.getQueryParameter(key);
		if (value == null) {
			return defaultValue;
		}
		else
		{
			try
			{
				return new BigDecimal(value);
			}
			catch (NumberFormatException e) {
				Log.e(Constants.LOG_TAG, e.toString());
				return defaultValue;
			}
		}
	}
	
	private String parseString(Uri uri, String key, String defaultValue) {
		String value = uri.getQueryParameter(key);
		if (value == null) {
			return defaultValue;
		}
		else
		{
			return value;
		}
	}
	
	/**
	 * Returns a string with our text arranged in a nice way
	 * 
	 * TODO: allow escaping via "%%", or escape string beforehand
	 * @return
	 */
	public CharSequence getLayout() {
		String ret = formattingLayout;
		
		for (String key : textRepresentation.keySet()) {
			ret = ret.replaceAll("%" + key, textRepresentation.get(key));
		}
		
		ret = ret.replaceAll("%i", makeItemsText().toString());
		
		return ret;
	}

	private CharSequence makeItemsText() {
		StringBuilder ret = new StringBuilder();
		
		for (ReceiptItem item : items) {
			ret.append(item.makeItemText());
		}
		
		return ret;
	}
}
