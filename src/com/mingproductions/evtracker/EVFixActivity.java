package com.mingproductions.evtracker;

import com.actionbarsherlock.app.ActionBar;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class EVFixActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		Bundle b = getIntent().getExtras();
		int position = b.getInt("pokemon");
		int gamePos = b.getInt("game");
		
		return EVFixFragment.newInstance(position, gamePos);
	}
	
	@Override
	public ActionBar getSupportActionBar()
	{
		return super.getSupportActionBar();
	}
	
}
