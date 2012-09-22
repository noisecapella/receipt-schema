package com.schneeloch.receiptreader;

import android.app.Activity;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
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
        
        textView = (TextView)findViewById(R.id.textView);

        Display display = getWindowManager().getDefaultDisplay();
        
        Uri uri = getIntent().getData();
        receiptData = new ReceiptData(uri, textView.getPaint(), display);
        textView.setText(receiptData.getLayout());
        
    }
}
