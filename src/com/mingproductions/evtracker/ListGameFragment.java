package com.mingproductions.evtracker;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mingproductions.evtracker.adapter.GameAdapter;
import com.mingproductions.evtracker.model.FragmentStorage;
import com.mingproductions.evtracker.model.GameStore;
import com.mingproductions.evtracker.model.PokemonGame;

public class ListGameFragment extends SherlockListFragment {

	public static final String EXTRA_GAME_POSITION = "com.mingproductions.evtracker.game_position";

	private ArrayList<PokemonGame> mAllGames;

	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		try {
			super.onCreate(savedInstanceBundle);

			setHasOptionsMenu(true);
			setRetainInstance(true);

			mAllGames = GameStore.sharedStore(getSherlockActivity()).allGames();

			GameAdapter adapter = new GameAdapter(mAllGames, getSherlockActivity());
			setListAdapter(adapter);
		} 
		catch (Exception ex)
		{
			FragmentStorage.sharedStore(getSherlockActivity()).clearFragmentList();
			getSherlockActivity().getSupportActionBar().selectTab(getSherlockActivity().getSupportActionBar().getTabAt(0));
		}
	}

	@Override
	public void onResume()
	{
		try {
			super.onResume();
			((GameAdapter)getListAdapter()).notifyDataSetChanged();
			getSherlockActivity().getSupportActionBar().setLogo(R.drawable.ic_launcher);
			getSherlockActivity().getSupportActionBar().setTitle("EV Tracker");
			getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			
			if (FragmentStorage.sharedStore(getSherlockActivity()).allFragmentsList() != null &&
					FragmentStorage.sharedStore(getSherlockActivity()).wasTabChanged())
			{
				for (Fragment f : FragmentStorage.sharedStore(getSherlockActivity()).allFragmentsList())
				{
					try 
					{
						getFragmentManager().beginTransaction().replace(R.id.host_view, f).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
					}
					catch (IllegalStateException e)
					{
						FragmentStorage.sharedStore(getSherlockActivity()).setTabChanged(false);
						FragmentStorage.sharedStore(getSherlockActivity()).clearFragmentList();
						getSherlockActivity().getSupportActionBar().selectTab(getSherlockActivity().getSupportActionBar().getTabAt(0));
					}
				}
				FragmentStorage.sharedStore(getSherlockActivity()).setTabChanged(false);
			}
			else if (!FragmentStorage.sharedStore(getSherlockActivity()).wasTabChanged())
			{
				FragmentStorage.sharedStore(getSherlockActivity()).clearFragmentList();
			}
		}
		catch (Exception ex)
		{
			FragmentStorage.sharedStore(getSherlockActivity()).clearFragmentList();
			getSherlockActivity().getSupportActionBar().selectTab(getSherlockActivity().getSupportActionBar().getTabAt(0));
		}
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_game_list, parent, false);

		Button newGameButton = (Button)v.findViewById(R.id.new_game_button);
		newGameButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getFragmentManager().beginTransaction().replace(R.id.host_view, new NewGameFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
			}
		});

		ListView listView = (ListView)v.findViewById(android.R.id.list);

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
		{
			registerForContextMenu(listView);
		} 
		else 
		{
			// Use contextual action bar on Honeycomb and higher
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
			listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

				@Override
				public boolean onPrepareActionMode(ActionMode mode, android.view.Menu menu) {
					// Required, but not used in this implementation
					return false;
				}

				@Override
				public void onDestroyActionMode(ActionMode mode) {
					// Required, but not used in this implementation

				}

				@Override
				public boolean onCreateActionMode(ActionMode mode, android.view.Menu menu) {
					android.view.MenuInflater inflater = mode.getMenuInflater();
					inflater.inflate(R.menu.fragment_game_delete, menu);
					return true;
				}

				@Override
				public boolean onActionItemClicked(final ActionMode mode, android.view.MenuItem item) {
					switch (item.getItemId())
					{
					case R.id.menu_item_delete_game:
					{
						final GameAdapter adapter = (GameAdapter)getListAdapter();
						
						Builder dialog = new AlertDialog.Builder(getSherlockActivity()).setTitle("Delete Games")
								.setMessage("Are you sure you want to delete these games?");
						dialog.setNegativeButton("No", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								mode.finish();
							}
						});
						dialog.setPositiveButton("Delete", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								for (int i = adapter.getCount() - 1; i >= 0; i--)
								{
									if (getListView().isItemChecked(i))
									{
										GameStore.sharedStore(getSherlockActivity()).removeGame(adapter.getItem(i));
									}
								}
								GameStore.sharedStore(getSherlockActivity()).saveGames();
								adapter.notifyDataSetChanged();
								mode.finish();
							}
						});
						dialog.create().show();
						return true;
					}
					default:
						return false;
					}
				}

				@Override
				public void onItemCheckedStateChanged(ActionMode mode, int position,
						long id, boolean checked) {
					// Required, but not used in this implementation

				}
			});
		}

		return v;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		getSherlockActivity().getMenuInflater().inflate(R.menu.fragment_game_delete, menu);
	}

	@Override
	public boolean onContextItemSelected(android.view.MenuItem item)
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int position = info.position;
		final GameAdapter adapter = (GameAdapter)getListAdapter();
		final PokemonGame game = adapter.getItem(position);

		switch(item.getItemId())
		{
		case R.id.menu_item_delete_game:
		{
			Builder dialog = new AlertDialog.Builder(getSherlockActivity()).setTitle("Delete Game")
					.setMessage("Are you sure you want to delete " + game.getGameName() + "?");
			dialog.setNegativeButton("No", null);
			dialog.setPositiveButton("Delete", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					GameStore.sharedStore(getSherlockActivity()).removeGame(game);
					adapter.notifyDataSetChanged();
				}
			});
			dialog.create().show();
			return true;
		}
		default:
			return false;
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		getFragmentManager().beginTransaction().replace(R.id.host_view, ListPokemonFragment.newInstance(position))
							.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_game_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.menu_item_new_game:
		{
			getFragmentManager().beginTransaction().replace(R.id.host_view, new NewGameFragment())
								.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
			return true;
		}
		default:
			return false;
		}
	}
}
