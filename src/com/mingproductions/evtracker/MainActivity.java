package com.mingproductions.evtracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class MainActivity extends SherlockFragmentActivity {
	
	private TabHost mTabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup();
		
		TabSpec tSpecTracking = mTabHost.newTabSpec("Tracking");
		tSpecTracking.setIndicator("Tracking");
		tSpecTracking.setContent(new Intent(getBaseContext(), ListGameActivity.class));
		mTabHost.addTab(tSpecTracking);
		
		TabSpec tSpecPokedex = mTabHost.newTabSpec("Pokedex");
		tSpecPokedex.setIndicator("Pokedex");
		tSpecPokedex.setContent(new Intent(getBaseContext(), ListPokedexActivity.class));
		mTabHost.addTab(tSpecPokedex);
	}

}
