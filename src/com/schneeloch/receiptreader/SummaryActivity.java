package com.schneeloch.receiptreader;

import android.app.Activity;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class SummaryActivity extends Activity
{
	private ReceiptData receiptData;
	private TextView textView;

	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Uri uri = getIntent().getData();
        receiptData = new ReceiptData(uri);
        
        textView = (TextView)findViewById(R.id.textView);
        textView.setText(receiptData.getLayout());
    }
}
