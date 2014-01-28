package com.mingproductions.evtracker;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragment;

public class RenamePokemonFragment extends SherlockFragment {

	public static RenamePokemonFragment newInstance(int position, int game)
	{
		Bundle args = new Bundle();
		args.putInt("mPokemon", position);
		args.putInt("game", game);
		
		RenamePokemonFragment fragment = new RenamePokemonFragment();
		fragment.setArguments(args);
		
		return fragment;
	}

}
