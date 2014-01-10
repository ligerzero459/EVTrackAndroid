package com.mingproductions.evtracker;

import java.util.ArrayList;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mingproductions.evtracker.model.EVPokemon;
import com.mingproductions.evtracker.model.GameStore;
import com.mingproductions.evtracker.model.GameTableStore;
import com.mingproductions.evtracker.model.PokedexStore;
import com.mingproductions.evtracker.model.PokemonGame;

public class GameListFragment extends SherlockListFragment {
	public static final String EXTRA_GAME_POSITION = "com.mingproductions.evtracker.game_position";
	
	private ArrayList<PokemonGame> allGames;
	
	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);
		
		PokedexStore.sharedStore(getActivity());
		GameTableStore.sharedStore(getActivity());
		
		setHasOptionsMenu(true);
		setRetainInstance(true);
		
		allGames = GameStore.sharedStore(getActivity()).allGames();
		
		GameAdapter adapter = new GameAdapter(allGames);
		setListAdapter(adapter);
		
		if (NavUtils.getParentActivityName(getActivity()) != null)
		{
			((GameListActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		((GameAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		// Starting Activity
		Intent i = new Intent(getActivity(), PokemonListActivity.class);
		
		Bundle b = new Bundle();
		b.putInt(EXTRA_GAME_POSITION, position);
		
		i.putExtras(b);
		startActivity(i);
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
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getActivity()) != null)
			{
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			return true;
		case R.id.menu_item_new_game:
			Intent i = new Intent(getActivity(), NewGameActivity.class);
			startActivity(i);
			return true;
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
