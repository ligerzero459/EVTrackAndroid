package com.mingproductions.evtracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.actionbarsherlock.app.ActionBar;

public class PokemonListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		Bundle b = getIntent().getExtras();
		
		int position = (int)b.getInt(GameListFragment.EXTRA_GAME_POSITION, 0);
		
		return PokemonListFragment.newInstance(position);
	}
	
	@Override
	public ActionBar getSupportActionBar()
	{
		return super.getSupportActionBar();
	}

}
