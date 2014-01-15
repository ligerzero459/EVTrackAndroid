package com.mingproductions.evtracker;

import android.support.v4.app.Fragment;

public class ListPokedexActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new ListPokedexFragment();
	}

}

