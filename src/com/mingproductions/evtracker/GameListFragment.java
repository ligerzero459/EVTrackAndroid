package com.mingproductions.evtracker;

import java.util.ArrayList;

import com.mingproductions.evtracker.model.EVPokemon;
import com.mingproductions.evtracker.model.GameStore;
import com.mingproductions.evtracker.model.PokedexStore;
import com.mingproductions.evtracker.model.PokemonGame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class GameListFragment extends ListFragment {
	private static final String EXTRA_GAME_POSITION = "com.mingproductions.evtracker.game_position";
	
	private ArrayList<PokemonGame> allGames;
	
	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);
		
		allGames = GameStore.sharedStore(getActivity()).allGames();
		
		PokedexStore.sharedStore(getActivity());
		
		GameAdapter adapter = new GameAdapter(allGames);
		setListAdapter(adapter);
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
		if (position == 0)
		{
			EVPokemon p = new EVPokemon(1, "Bulbasaur", 2130837506);
			EVPokemon p2 = new EVPokemon(2, "Ivysaur", 2130837507);
			
			allGames.get(position).getAllPokemon().add(p);
			allGames.get(position).getAllPokemon().add(p2);
		}
		else
		{
			EVPokemon p = new EVPokemon(3, "Venusaur", 2130837508);
			EVPokemon p2 = new EVPokemon(4, "Charmander", 2130837509);
			
			allGames.get(position).getAllPokemon().add(p);
			allGames.get(position).getAllPokemon().add(p2);
		}
		
		// Starting Activity
		Intent i = new Intent(getActivity(), PokemonListActivity.class);
		
		Bundle b = new Bundle();
		b.putInt(EXTRA_GAME_POSITION, position);
		
		i.putExtras(b);
		startActivity(i);
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
			
			ImageView gameImage = (ImageView)convertView.findViewById(R.id.game_image);
			gameImage.setImageResource(game.getImageId());
			
			TextView gameNameText = (TextView)convertView.findViewById(R.id.game_name);
			gameNameText.setText(game.getGameName());
			
			return convertView;
		}
	}
}
