package com.mingproductions.evtracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.bugsense.trace.BugSenseHandler;
import com.mingproductions.evtracker.adapter.TabsPagerAdapter;
import com.mingproductions.evtracker.model.FragmentStorage;

public class MainActivity extends SherlockFragmentActivity implements ActionBar.TabListener {
	
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
	
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
        
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
 
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		BugSenseHandler.startSession(this);
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		BugSenseHandler.closeSession(this);
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
