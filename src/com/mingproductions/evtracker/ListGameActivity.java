package com.mingproductions.evtracker;

import com.actionbarsherlock.app.ActionBar;

import android.support.v4.app.Fragment;

public class ListGameActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new ListGameFragment();
	}
	
	@Override
	public ActionBar getSupportActionBar()
	{
		return super.getSupportActionBar();
	}

}
