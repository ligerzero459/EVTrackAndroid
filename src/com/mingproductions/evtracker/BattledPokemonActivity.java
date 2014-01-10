package com.mingproductions.evtracker;

import com.actionbarsherlock.app.ActionBar;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BattledPokemonActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		Bundle b = getIntent().getExtras();
		int pokemonPos = (int)b.getInt("pokemon");
		int gamePos = (int)b.getInt("game");
		
		return BattledPokemonFragment.newInstance(pokemonPos, gamePos);
	}
	
	@Override
	public ActionBar getSupportActionBar()
	{
		return super.getSupportActionBar();
	}

}
