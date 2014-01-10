package com.mingproductions.evtracker;

import com.actionbarsherlock.app.ActionBar;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class NewPokemonActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		Bundle b = getIntent().getExtras();
		int position = (int)b.getInt(GameListFragment.EXTRA_GAME_POSITION);
		
		return NewPokemonFragment.newInstance(position);
	}
	
	@Override
	public ActionBar getSupportActionBar()
	{
		return super.getSupportActionBar();
	}

}
