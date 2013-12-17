package com.mingproductions.evtracker;

import java.util.ArrayList;

import com.mingproductions.evtracker.model.EVPokemon;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class PokemonListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		Bundle b = getIntent().getExtras();
		
		int position = (int)b.getInt(PokemonListFragment.EXTRA_GAME_POSITION, 0);
		
		return PokemonListFragment.newInstance(position);
	}

}
