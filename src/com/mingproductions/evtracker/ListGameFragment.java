package com.mingproductions.evtracker;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.res.Resources;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mingproductions.evtracker.model.FragmentStorage;
import com.mingproductions.evtracker.model.GameStore;
import com.mingproductions.evtracker.model.GameTableStore;
import com.mingproductions.evtracker.model.PokedexStore;
import com.mingproductions.evtracker.model.PokemonGame;

public class ListGameFragment extends SherlockListFragment {
	// TODO: Social tab

	public static final String EXTRA_GAME_POSITION = "com.mingproductions.evtracker.game_position";

	private ArrayList<PokemonGame> mAllGames;

	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);

		PokedexStore.sharedStore(getActivity());
		GameTableStore.sharedStore(getActivity());

		setHasOptionsMenu(true);
		setRetainInstance(true);

		mAllGames = GameStore.sharedStore(getActivity()).allGames();

		GameAdapter adapter = new GameAdapter(mAllGames);
		setListAdapter(adapter);
	}

	@Override
	public void onResume()
	{
		super.onResume();
		((GameAdapter)getListAdapter()).notifyDataSetChanged();
		getSherlockActivity().getSupportActionBar().setLogo(R.drawable.ic_launcher);
		getSherlockActivity().getSupportActionBar().setTitle("EV Tracker");
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		
		if (FragmentStorage.sharedStore(getActivity()).allFragmentsList() != null && 
				FragmentStorage.sharedStore(getActivity()).wasTabChanged())
		{
			for (Fragment f : FragmentStorage.sharedStore(getActivity()).allFragmentsList())
			{
				getFragmentManager().beginTransaction().replace(R.id.host_view, f).addToBackStack(null).commit();
			}
			FragmentStorage.sharedStore(getActivity()).setTabChanged(false);
		}
		else if (!FragmentStorage.sharedStore(getActivity()).wasTabChanged())
		{
			FragmentStorage.sharedStore(getActivity()).clearFragmentList();
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
				// TODO: Examine difference between add and replace for FragmentTransaction
				getFragmentManager().beginTransaction().replace(R.id.host_view, new NewGameFragment()).addToBackStack(null).commit();
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
				public boolean onActionItemClicked(ActionMode mode, android.view.MenuItem item) {
					switch (item.getItemId())
					{
					case R.id.menu_item_delete_game:
					{
						GameAdapter adapter = (GameAdapter)getListAdapter();

						for (int i = adapter.getCount() - 1; i >= 0; i--)
						{
							if (getListView().isItemChecked(i))
							{
								GameStore.sharedStore(getActivity()).removeGame(adapter.getItem(i));
							}
						}
						mode.finish();
						GameStore.sharedStore(getActivity()).saveGames();
						adapter.notifyDataSetChanged();
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
		getActivity().getMenuInflater().inflate(R.menu.fragment_game_delete, menu);
	}

	@Override
	public boolean onContextItemSelected(android.view.MenuItem item)
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int position = info.position;
		GameAdapter adapter = (GameAdapter)getListAdapter();
		PokemonGame game = adapter.getItem(position);

		switch(item.getItemId())
		{
		case R.id.menu_item_delete_game:
		{
			GameStore.sharedStore(getActivity()).removeGame(game);
			adapter.notifyDataSetChanged();
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
							.addToBackStack(null).commit();
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
								.addToBackStack(null).commit();
			return true;
		}
		default:
			return false;
		}
	}

	private class GameAdapter extends ArrayAdapter<PokemonGame>
	{
		public GameAdapter(ArrayList<PokemonGame> allGames)
		{
			super(getActivity(), 0, allGames);

		}

		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_game, null);
			}

			PokemonGame game = getItem(position);
			Resources resource = getResources();

			ImageView gameImage = (ImageView)convertView.findViewById(R.id.game_image);
			gameImage.setImageResource(resource.getIdentifier("com.mingproductions.evtracker:drawable/" 
					+ game.getImageName(), null, null));

			TextView gameNameText = (TextView)convertView.findViewById(R.id.game_name);
			gameNameText.setText(game.getGameName());

			return convertView;
		}
	}
}
