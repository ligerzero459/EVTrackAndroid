package com.mingproductions.evtracker;

import com.actionbarsherlock.app.ActionBar;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class EVBattledPokemonActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		Bundle b = getIntent().getExtras();
		int pokemonPos = (int)b.getInt("pokemon");
		int gamePos = (int)b.getInt("game");
		
		return EVBattledPokemonFragment.newInstance(pokemonPos, gamePos);
	}
	
	@Override
	public ActionBar getSupportActionBar()
	{
		return super.getSupportActionBar();
	}

}
