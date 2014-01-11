package com.mingproductions.evtracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.actionbarsherlock.app.ActionBar;

public class ListPokemonActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		Bundle b = getIntent().getExtras();
		
		int position = (int)b.getInt(ListGameFragment.EXTRA_GAME_POSITION, 0);
		
		return ListPokemonFragment.newInstance(position);
	}
	
	@Override
	public ActionBar getSupportActionBar()
	{
		return super.getSupportActionBar();
	}

}
