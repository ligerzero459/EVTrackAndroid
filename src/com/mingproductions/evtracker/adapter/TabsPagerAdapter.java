package com.mingproductions.evtracker.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mingproductions.evtracker.ListGameFragment;
import com.mingproductions.evtracker.ListPokedexFragment;
import com.mingproductions.evtracker.SocialFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter
{
	public TabsPagerAdapter(FragmentManager fm)
	{
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch (index)
		{
		case 0:
			return new ListGameFragment();
		case 1:
			return new ListPokedexFragment();
		case 2:
			return new SocialFragment();
		}
		
		return null;
	}

	@Override
	public int getCount() {
		return 2;
	}
}
