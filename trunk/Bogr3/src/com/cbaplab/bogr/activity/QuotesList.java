package com.cbaplab.bogr.activity;

import android.app.ListActivity;
import android.os.Bundle;

import com.cbaplab.bogr.R;

public class QuotesList extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quotes_list);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();		
	}
	

}
