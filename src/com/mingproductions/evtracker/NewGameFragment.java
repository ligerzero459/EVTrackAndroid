package com.mingproductions.evtracker;

import java.util.ArrayList;

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
import com.actionbarsherlock.view.MenuItem;
import com.mingproductions.evtracker.model.GameStore;
import com.mingproductions.evtracker.model.GameTableStore;
import com.mingproductions.evtracker.model.PokemonGame;

public class NewGameFragment extends SherlockListFragment {
	
	private ArrayList<PokemonGame> mAllGames;
	
	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);
		setRetainInstance(true);
		
		mAllGames = GameTableStore.sharedStore(getActivity()).allGames();
		
		GameAdapter adapter = new GameAdapter(mAllGames);
		setListAdapter(adapter);
		
		if (NavUtils.getParentActivityName(getActivity()) != null)
		{
			((NewGameActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		PokemonGame newGame = mAllGames.get(position);
		
		GameStore.sharedStore(getActivity()).addGame(newGame);
		GameStore.sharedStore(getActivity()).saveGames();
		
		getActivity().finish();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getActivity()) != null)
			{
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
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
