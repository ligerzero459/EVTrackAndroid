package com.mingproductions.evtracker;

import java.util.ArrayList;

import com.mingproductions.evtracker.model.EVPokemon;
import com.mingproductions.evtracker.model.GameStore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PokemonListFragment extends ListFragment {
	public static final String EXTRA_GAME_POSITION = "com.mingproductions.evtracker.game_position";
	
	private ArrayList<EVPokemon> allPokemon;
	private int gamePos;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);
		gamePos = (int)getArguments().getInt(EXTRA_GAME_POSITION);
		allPokemon = GameStore.sharedStore(getActivity()).allGames().get(gamePos).getAllPokemon();
		
		// Set the title & image to the correct game
		getActivity().setTitle(GameStore.sharedStore(getActivity()).allGames().get(gamePos).getGameName());
		
		if (Build.VERSION.RELEASE.startsWith("4."))
		{
			getActivity().getActionBar().setLogo(GameStore.sharedStore(getActivity()).allGames().get(gamePos).getImageId());
		}
		
		PokemonAdapter adapter = new PokemonAdapter(allPokemon);
		setListAdapter(adapter);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		((PokemonAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Intent i = new Intent(getActivity(), EVDetailActivity.class);
		
		Bundle b = new Bundle();
		b.putInt("pokemon", position);
		b.putInt("game", gamePos);
		i.putExtras(b);
		
		startActivity(i);
	}
	
	private class PokemonAdapter extends ArrayAdapter<EVPokemon>
	{
		public PokemonAdapter(ArrayList<EVPokemon> allPokemon)
		{
			super(getActivity(), 0, allPokemon);
		}
		
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_ev_pokemon, null);
			}
			
			EVPokemon p = getItem(position);
			
			ImageView pokemonImage = (ImageView)convertView.findViewById(R.id.image_pokemon);
			pokemonImage.setImageResource(p.getImageResource());
			
			TextView pokemonName = (TextView)convertView.findViewById(R.id.name_pokemon);
			pokemonName.setText(p.getPokemonName());
			
			return convertView;
		}
	}
	
	public static PokemonListFragment newInstance(int position)
	{
		Bundle args = new Bundle();
		args.putInt(EXTRA_GAME_POSITION, position);
		
		PokemonListFragment fragment = new PokemonListFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
}
