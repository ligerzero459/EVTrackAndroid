package com.mingproductions.evtracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.mingproductions.evtracker.model.FragmentStorage;

public class MainActivity extends SherlockFragmentActivity implements ActionBar.TabListener {
	// TODO: Implement OnBackButtonPressed
	
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
	
	private String[] tabs = { "Tracker", "Pokedex" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_2);
		
		 // Initialization
        actionBar = getSupportActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
 
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
	}
	
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
			}
			
			return null;
		}

		@Override
		public int getCount() {
			return 2;
		}
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.replace(R.id.host_view, mAdapter.getItem(tab.getPosition()));
		FragmentStorage.sharedStore(this).setTabChanged(true);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}
	
	@Override
	public ActionBar getSupportActionBar()
	{
		return super.getSupportActionBar();
	}
	
	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		int lastFragmentIndex = FragmentStorage.sharedStore(this).indexOfLastFragment();
		Fragment removeF = FragmentStorage.sharedStore(this).getFragmentAtIndex(lastFragmentIndex);
		
		FragmentStorage.sharedStore(this).removeFragmentFromList(removeF);
	}

}
