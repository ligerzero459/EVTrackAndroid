package com.mingproductions.evtracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BattledPokemonActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		Bundle b = getIntent().getExtras();
		int pokemonPos = b.getInt("pokemon");
		int gamePos = b.getInt("game");
		
		return BattledPokemonFragment.newInstance(pokemonPos, gamePos);
	}

}
