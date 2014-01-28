package com.mingproductions.evtracker;

import java.util.Date;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.bugsense.trace.BugSenseHandler;
import com.google.analytics.tracking.android.EasyTracker;
import com.mingproductions.evtracker.adapter.TabsPagerAdapter;
import com.mingproductions.evtracker.model.FragmentStorage;
import com.mingproductions.evtracker.model.GameStore;
import com.mingproductions.evtracker.model.GameTableStore;
import com.mingproductions.evtracker.model.PokedexStore;

public class MainActivity extends SherlockFragmentActivity implements ActionBar.TabListener {
	
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    private static Date lastRun;
    public static SharedPreferences prefs;
    private boolean firstRun = true;
	
	private String[] tabs = { "Tracker", "Pokedex", "Social" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		BugSenseHandler.initAndStartSession(this, "25efee78");
		setContentView(R.layout.activity_main_2);
		
		 // Initialization
        actionBar = getSupportActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        prefs = getSharedPreferences("evtracker_prefs", MODE_PRIVATE);
        firstRun = prefs.getBoolean("firstRun", true);
        
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
 
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }
        
        if (firstRun)
        {
        	GameStore.sharedStore(this, firstRun);
        	
        	firstRun = false;
        	SharedPreferences.Editor editor = prefs.edit();
        	editor.putBoolean("firstRun", false);
        	editor.commit();
        }
        
        PokedexStore.sharedStore(this);
        GameTableStore.sharedStore(this);
		
		lastRun = new Date();
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
		BugSenseHandler.startSession(this);
	}
	
	@Override
	public void onResume()
	{
		Date restart = new Date();
		long secondsInBackground = (restart.getTime() - lastRun.getTime()) / 1000;
		if (secondsInBackground >= 900)
		{
			getSupportActionBar().selectTab(getSupportActionBar().getTabAt(0));
		}
		super.onResume();
	}
	
	@Override
	public void onPause()
	{
		lastRun = new Date();
		super.onPause();
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		BugSenseHandler.closeSession(this);
		EasyTracker.getInstance(this).activityStop(this);
	}
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
		ft.replace(R.id.host_view, mAdapter.getItem(tab.getPosition()));
		FragmentStorage.sharedStore(this).setTabChanged(true);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		if (tab.getPosition() == 0)
		{
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.replace(R.id.host_view, mAdapter.getItem(tab.getPosition()));
			FragmentStorage.sharedStore(this).clearFragmentList();
		}
		else if (tab.getPosition() == 1)
		{
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.replace(R.id.host_view, mAdapter.getItem(tab.getPosition()));
		}
	}
	
	@Override
	public ActionBar getSupportActionBar()
	{
		return super.getSupportActionBar();
	}
	
	@Override
	public void onBackPressed()
	{
		if (getSupportActionBar().getSelectedTab().getPosition() == 0)
		{
			int lastFragmentIndex = FragmentStorage.sharedStore(this).indexOfLastFragment();
			if (lastFragmentIndex > 0)
			{
				Fragment removeF = FragmentStorage.sharedStore(this).getFragmentAtIndex(lastFragmentIndex);

				FragmentStorage.sharedStore(this).removeFragmentFromList(removeF);
			}
		}
		super.onBackPressed();
	}

}
