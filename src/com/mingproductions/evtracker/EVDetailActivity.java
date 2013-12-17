package com.mingproductions.evtracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class EVDetailActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		Bundle b = getIntent().getExtras();
		int position = b.getInt("pokemon");
		int gamePos = b.getInt("game");
		
		return EVDetailFragment.newInstance(position, gamePos);
	}

}
