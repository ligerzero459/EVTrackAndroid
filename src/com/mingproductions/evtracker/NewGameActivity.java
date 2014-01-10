package com.mingproductions.evtracker;

import com.actionbarsherlock.app.ActionBar;

import android.support.v4.app.Fragment;

public class NewGameActivity extends SingleFragmentActivity {
	
	protected Fragment createFragment() {
		return new NewGameFragment();
	}
	
	@Override
	public ActionBar getSupportActionBar()
	{
		return super.getSupportActionBar();
	}

}
