package com.mingproductions.evtracker.model;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.Fragment;

public class FragmentStorage {
	
	private ArrayList<Fragment> allFragmentsList;
	
	private static FragmentStorage sharedStore;
	private Context mAppContext;
	private boolean mTabChanged;
	
	public FragmentStorage(Context appContext)
	{
		mAppContext = appContext;
		allFragmentsList = new ArrayList<Fragment>();
		mTabChanged = false;
	}
	
	public static FragmentStorage sharedStore(Context c)
	{
		if (sharedStore == null)
		{
			sharedStore = new FragmentStorage(c.getApplicationContext());
		}
		
		return sharedStore;
	}
	
	public ArrayList<Fragment> allFragmentsList()
	{
		return allFragmentsList;
	}
	
	public void addFragmentToList(Fragment fragment)
	{
		allFragmentsList.add(fragment);
	}
	
	public void removeFragmentFromList(Fragment fragment)
	{
		allFragmentsList.remove(fragment);
	}
	
	public void clearFragmentList()
	{
		allFragmentsList.clear();
	}
	
	public Fragment getFragmentAtIndex(int index)
	{
		return allFragmentsList.get(index);
	}
	
	public int indexOfLastFragment()
	{
		return allFragmentsList.size() - 1;
	}
	
	public void setTabChanged(boolean changed)
	{
		mTabChanged = changed;
	}
	
	public boolean wasTabChanged()
	{
		return mTabChanged;
	}

}
